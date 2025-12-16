package com.log_injestor.service1.controller;

import com.log_injestor.service1.service.LogProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("injestor")
public class InjestorController {

    private final LogProducerService service;

    public InjestorController(LogProducerService service ) {
        this.service = service;
    }

    @PostMapping("receiveLog")
    public void receiveLog(@RequestBody String log) {
        service.sendLogMessage(log);

    }
}
