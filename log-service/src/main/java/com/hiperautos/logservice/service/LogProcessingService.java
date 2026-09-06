package com.hiperautos.logservice.service;

import com.hiperautos.logservice.dto.LogInputDTO;
import com.hiperautos.logservice.model.LogAnalysisResponse;
import com.hiperautos.logservice.repository.LogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Bean separado responsável pelo processamento assíncrono de logs.
 *
 * IMPORTANTE: esta classe deve ser um bean separado do controller.
 * O @Async funciona via proxy AOP do Spring — se o método fosse chamado
 * com this.processAsync() de dentro da mesma classe, o proxy seria
 * bypassado e a execução seria síncrona (sem erro ou aviso).
 *
 * Fluxo:
 * 1. Chama a AI para analisar o log
 * 2. Se a AI falhar: salva um registro com status "analysis_failed"
 * 3. Sempre preenche os campos do log original (containerName, stream, etc.)
 * 4. Persiste no PostgreSQL
 *
 * Nenhum log é perdido silenciosamente, mesmo com a AI fora do ar.
 */
@Service
public class LogProcessingService {

    private final LogAnalyzerAIService aiService;
    private final LogRepository repository;

    public LogProcessingService(LogAnalyzerAIService aiService, LogRepository repository) {
        this.aiService = aiService;
        this.repository = repository;
    }

    /**
     * @Async("logProcessingExecutor") — o nome do executor é obrigatório.
     * Sem o nome, o Spring usaria o executor padrão (SimpleAsyncTaskExecutor,
     * sem pool nem limites), anulando a configuração do AsyncConfig.
     */
    @Async("logProcessingExecutor")
    public void processAsync(LogInputDTO log) {
        LogAnalysisResponse result;

        try {
            result = aiService.analyze(log);
            System.out.println("[AI] Análise concluída para container: " + log.getContainerName()
                    + " | Status: " + result.getStatus());
        } catch (Exception e) {
            // AI falhou: cria registro de fallback para não perder o log original
            System.err.println("[AI] Falha na análise para container: " + log.getContainerName()
                    + " | Motivo: " + e.getMessage());
            result = new LogAnalysisResponse();
            result.setStatus("analysis_failed");
            result.setCategory("unknown");
            result.setSummary("AI analysis unavailable: " + e.getMessage());
            result.setSuggestedAction("Review log manually or retry analysis.");
        }

        // Campos do log original — sempre preenchidos, independente da AI
        result.setOriginalLog(log.getMessage());
        result.setContainerName(log.getContainerName());
        result.setStream(log.getStream());
        result.setImage(log.getImage());
        result.setTimestamp(log.getTimestamp()); // bug fix: campo nunca era preenchido antes

        try {
            repository.save(result);
            System.out.println("[DB] Log salvo | Container: " + log.getContainerName()
                    + " | Status: " + result.getStatus());
        } catch (Exception e) {
            System.err.println("[DB] Erro ao salvar log no banco: " + e.getMessage());
        }
    }
}
