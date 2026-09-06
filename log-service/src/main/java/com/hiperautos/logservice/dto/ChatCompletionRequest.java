package com.hiperautos.logservice.dto;

import java.util.List;

public class ChatCompletionRequest {

    private String model;
    private List<ChatCompletionMessage> messages;
    private Double temperature;
    private Integer max_tokens;

    public ChatCompletionRequest(String model, List<ChatCompletionMessage> messages, Double temperature, Integer max_tokens) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }

    public ChatCompletionRequest() {}

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

    public Integer getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(Integer max_tokens) {
        this.max_tokens = max_tokens;
    }
}
