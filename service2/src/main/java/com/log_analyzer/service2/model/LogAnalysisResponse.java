package com.log_analyzer.service2.model;

import java.io.Serializable;

public class LogAnalysisResponse implements Serializable {

    private String stream;
    private String containerName;
    private String image;
    private String status;
    private String category;
    private String summary;
    private String originalLog;
    private String suggestedAction;

    public LogAnalysisResponse(){

    }
    public LogAnalysisResponse(String status, String category, String summary, String originalLog, String suggestedAction) {
        this.status = status;
        this.category = category;
        this.summary = summary;
        this.originalLog = originalLog;
        this.suggestedAction = suggestedAction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getOriginalLog() {
        return originalLog;
    }

    public void setOriginalLog(String originalLog) {
        this.originalLog = originalLog;
    }

    public String getSuggestedAction() {
        return suggestedAction;
    }

    public void setSuggestedAction(String suggestedAction) {
        this.suggestedAction = suggestedAction;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
