package com.log_injestor.service1.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.log_injestor.service1.model.LogtDTO;
import com.log_injestor.service1.service.LogProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("injestor")
public class InjestorController {

    @Autowired
    private LogProducerService service;

    private LogtDTO dto = new LogtDTO();

    @PostMapping
    public void receiveLogs(@RequestBody List<LogtDTO> logs) {
        for (LogtDTO log : logs) {
            System.out.println("Erro recebido do container: " + log.getContainer_name());

            service.sendLogMessage(log);
        }
    }
}
