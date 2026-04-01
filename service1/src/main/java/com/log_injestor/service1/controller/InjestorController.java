package com.log_injestor.service1.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.log_injestor.service1.service.LogProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.log_injestor.service1.model.RequestDTO;

@RestController
@RequestMapping("injestor")
public class InjestorController {

    @Autowired
    private LogProducerService service;

    private RequestDTO dto = new RequestDTO();

    @PostMapping
    public void receiveLog(@RequestBody JsonNode json) {
        dto.setHost(json.path(0).path("host").asText());
        dto.setMessage(json.path(0).path("message").asText());
        dto.setTimestamp(json.path(0).path("timestamp").asText());
        dto.setFile(json.path(0).path("file").asText());

        service.sendLogMessage(dto);
    }
}
