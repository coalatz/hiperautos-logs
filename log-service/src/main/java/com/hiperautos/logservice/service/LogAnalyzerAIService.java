package com.hiperautos.logservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiperautos.logservice.dto.ChatCompletionMessage;
import com.hiperautos.logservice.dto.ChatCompletionRequest;
import com.hiperautos.logservice.dto.LogInputDTO;
import com.hiperautos.logservice.model.LogAnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

/**
 * Responsável exclusivamente por chamar a API de AI (Groq) e retornar
 * o resultado da análise.
 *
 * NOTA: o uso de .block() é seguro aqui porque este método é sempre chamado
 * a partir de uma thread @Async (ThreadPoolTaskExecutor), nunca de uma thread
 * do event loop do Project Reactor. Não há risco de deadlock.
 */
@Service
public class LogAnalyzerAIService {

    private final WebClient webClient;

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    @Value("${api.groq.key}")
    private String apiKey;

    private static final String AI_MODEL = "qwen/qwen3.8-27b";

    private static final String SYSTEM_PROMPT =
            "Você é um serviço de Inteligência Artificial especializado em análise automática de logs " +
            "de sistemas distribuídos baseados em microsserviços rodando em ambientes conteinerizados.\n\n" +
            "Sua tarefa é analisar o log fornecido e identificar o estado da ocorrência, o tipo de evento, " +
            "um resumo técnico objetivo, e uma sugestão para resolver o problema do log que foi enviado.\n\n" +
            "Utilize todo o contexto enviado junto com a mensagem para que consiga analisar e sugerir a melhor solução possível.\n\n" +
            "Regras obrigatórias:\n" +
            "- Retorne EXCLUSIVAMENTE um objeto JSON válido.\n" +
            "- Não inclua texto explicativo, comentários, markdown ou qualquer conteúdo fora do JSON.\n" +
            "- Utilize apenas os campos: status, category, summary, suggestedAction.\n" +
            "- Caso não seja possível identificar alguma informação com clareza, utilize valores neutros como \"unknown\".\n\n" +
            "Definição dos campos:\n" +
            "- status: estado geral do evento (\"success\", \"warning\", \"error\", \"critical\").\n" +
            "- category: tipo do log (\"authentication\", \"database\", \"network\", \"performance\", \"application\", \"infrastructure\").\n" +
            "- summary: resumo técnico curto e objetivo do que ocorreu.\n" +
            "- suggestedAction: sugestão de ação com base no log enviado.\n\n" +
            "Informações para análise:";

    public LogAnalyzerAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Chama a API de AI e retorna um LogAnalysisResponse com os campos da AI preenchidos.
     * Lança RuntimeException em caso de falha, para que o chamador possa tratar e salvar
     * um registro de fallback — garantindo que nenhum log seja perdido silenciosamente.
     */
    public LogAnalysisResponse analyze(LogInputDTO log) {
        String userMessage =
                "Local de saída dos dados: " + log.getStream() + "\n" +
                "Nome do container: " + log.getContainerName() + "\n" +
                "Imagem do container: " + log.getImage() + "\n" +
                "Timestamp: " + log.getTimestamp() + "\n" +
                "Log: " + log.getMessage();

        ChatCompletionRequest request = new ChatCompletionRequest(
                AI_MODEL,
                List.of(
                        new ChatCompletionMessage("system", SYSTEM_PROMPT),
                        new ChatCompletionMessage("user", userMessage)
                ),
                0.1,
                800
        );

        try {
            String responseBody = webClient.post()
                    .uri(API_URL)
                    .header("Authorization", "Bearer " + apiKey.trim())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // seguro: thread @Async, não é event loop do Reactor


            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            String rawJson = rootNode.path("choices").get(0)
                    .path("message").path("content").asText();

            // Remove possíveis blocos de markdown que o modelo possa retornar
            String cleanJson = rawJson
                    .replace("```json", "")
                    .replace("```JSON", "")
                    .replace("```", "")
                    .trim();

            return mapper.readValue(cleanJson, LogAnalysisResponse.class);

        } catch (WebClientResponseException e) {
            System.err.println("Groq API Error Status: " + e.getStatusCode());
            System.err.println("Groq API Error Body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Falha na API da AI: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao parsear resposta da AI: " + e.getMessage(), e);
        }
    }
}
