package com.log_analyzer.service2.model.dto;

import java.util.List;

public class ChatCompletionRequest {
    private String model;
    private List<ChatCompletionMessage> messages;
    private Double temperature;

    public ChatCompletionRequest(String model, List<ChatCompletionMessage> messages, Double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    public ChatCompletionRequest() {

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatCompletionMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatCompletionMessage> messages) {
        this.messages = messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
