package com.log_storage.service3.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class LogAnalysisResponse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String suggestedAction;

    @Column(columnDefinition = "TEXT")
    private String originalLog;

    private String containerName;
    private String image;
    private String stream;
    private String timestamp;

    public LogAnalysisResponse() {}

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

    public String getContainerName() {
        return containerName;
    }

    public String getImage() {
        return image;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }
}

