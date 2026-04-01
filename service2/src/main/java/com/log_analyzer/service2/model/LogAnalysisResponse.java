package com.log_analyzer.service2.model;

import java.io.Serializable;

public class LogAnalysisResponse implements Serializable {

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
}
