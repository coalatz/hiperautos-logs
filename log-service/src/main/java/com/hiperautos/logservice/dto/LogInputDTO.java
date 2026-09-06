package com.hiperautos.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO de entrada: representa um log recebido via POST /injestor.
 *
 * O campo containerName usa @JsonProperty para mapear o campo JSON "container_name"
 * (snake_case do cliente) para o nome Java convencional (camelCase), sem depender
 * de configurações globais do Jackson.
 */
public class LogInputDTO implements Serializable {

    private String message;
    private String timestamp;

    @JsonProperty("container_name")
    private String containerName;

    private String stream;
    private String image;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
