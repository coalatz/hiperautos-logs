package com.log_analyzer.service2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.log_analyzer.service2.model.LogAnalysisResponse;
import com.log_analyzer.service2.model.dto.ChatCompletionMessage;
import com.log_analyzer.service2.model.dto.ChatCompletionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogAnalyzerAIService {

    private final WebClient webClient;
    @Value("${api.gemini.url}")
    private String apiUrl;
    @Value("${api.gemini.key}")
    private String apiKey;
    private final String prompt = "Você é um serviço de Inteligência Artificial especializado em análise automática de logs de sistemas distribuídos baseados em microsserviços.\n" +
            "\n" +
            "Sua tarefa é analisar o log fornecido e identificar o estado da ocorrência, o tipo de evento e um resumo técnico objetivo.\n" +
            "\n" +
            "Regras obrigatórias:\n" +
            "\n" +
            "Retorne EXCLUSIVAMENTE um objeto JSON válido.\n" +
            "\n" +
            "Não inclua texto explicativo, comentários, markdown ou qualquer conteúdo fora do JSON.\n" +
            "\n" +
            "Utilize apenas os campos especificados, exatamente com estes nomes:\n" +
            "\n" +
            "status\n" +
            "\n" +
            "category\n" +
            "\n" +
            "summary\n" +
            "\n" +
            "suggestedAction\n" +
            "\n" +
            "Caso não seja possível identificar alguma informação com clareza, utilize valores neutros como \"unknown\" ou \"undetermined\".\n" +
            "\n" +
            "Definição dos campos:\n" +
            "\n" +
            "status: indica o estado geral do evento (ex: \"success\", \"warning\", \"error\", \"critical\").\n" +
            "\n" +
            "category: classifica o tipo do log (ex: \"authentication\", \"database\", \"network\", \"performance\", \"application\", \"infrastructure\").\n" +
            "\n" +
            "summary: resumo técnico curto e objetivo, em linguagem formal, descrevendo o que ocorreu.\n" +
            "\n" +
            "suggestedAction: sugestao de uma acao do que fazer com base no log enviado\n" +
            "\n" +
            "Log para análise:";

    public LogAnalyzerAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public LogAnalysisResponse sendLogAI(String log) {
        ChatCompletionMessage requestSystem = new ChatCompletionMessage("system", prompt);
        ChatCompletionMessage requestUser = new ChatCompletionMessage("user", log);
        List<ChatCompletionMessage> messages = List.of(requestSystem, requestUser);
        LogAnalysisResponse finalResponse = null;

        ChatCompletionRequest request = new ChatCompletionRequest("llama-3.1-8b-instant", messages, 0.1);

        try {
            String responseBody = webClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            String jsonLimpio = rootNode.path("choices").get(0).path("message").path("content").asText();
            finalResponse = mapper.readValue(jsonLimpio, LogAnalysisResponse.class);
            finalResponse.setOriginalLog(log);
        } catch (WebClientResponseException e) {
        System.out.println("Erro detalhado da API: " + e.getResponseBodyAsString());
    } catch (Exception e) {
        System.out.println("Erro genérico: " + e.getMessage());
    }

        return finalResponse;
    }
}
