package com.hiperautos.logservice.controller;

import com.hiperautos.logservice.dto.LogInputDTO;
import com.hiperautos.logservice.service.LogProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

/**
 * Endpoint de ingestão de logs.
 *
 * POST /injestor       — aceita lista de logs (clientes diretos da API)
 * POST /injestor/event — aceita um único log (usado pelo Vector.dev)
 *
 * Ambos retornam 202 Accepted imediatamente sem aguardar análise da AI.
 * Em sobrecarga (pool + fila cheios), retorna 503.
 */
@RestController
@RequestMapping("injestor")
public class LogController {

    private final LogProcessingService processingService;

    public LogController(LogProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping
    public ResponseEntity<Void> receiveLogs(@RequestBody List<LogInputDTO> logs) {
        return dispatch(logs);
    }

    @PostMapping("/event")
    public ResponseEntity<Void> receiveEvent(@RequestBody LogInputDTO log) {
        return dispatch(List.of(log));
    }

    private ResponseEntity<Void> dispatch(List<LogInputDTO> logs) {
        try {
            logs.forEach(log -> {
                System.out.println("[INGESTOR] Log recebido do container: " + log.getContainerName());
                processingService.processAsync(log);
            });
            return ResponseEntity.accepted().build();
        } catch (RejectedExecutionException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}

