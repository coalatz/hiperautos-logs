package com.log_analyzer.service2.model.dto;

public class ChatCompletionMessage {

    private String role;
    private String content;

    public ChatCompletionMessage( String role, String content) {
        this.role = role;
        this.content = content;
    }

    public ChatCompletionMessage() {

    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
