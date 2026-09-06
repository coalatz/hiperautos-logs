package com.hiperautos.logservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidade JPA que representa o resultado da análise de um log pelo sistema.
 *
 * Os campos status, category, summary e suggestedAction são preenchidos
 * pela AI. Os demais campos (originalLog, containerName, image, stream,
 * timestamp) são preenchidos diretamente do log de entrada, garantindo
 * que nenhuma informação seja perdida mesmo em caso de falha da AI.
 */
@Entity
@Table(name = "log_analysis")
public class LogAnalysisResponse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos preenchidos pela AI
    private String status;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String suggestedAction;

    // Campos do log original
    @Column(columnDefinition = "TEXT")
    private String originalLog;

    private String containerName;
    private String image;
    private String stream;
    private String timestamp;

    // Preenchido automaticamente na hora do INSERT — usado pelo Grafana para filtros de tempo
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public LogAnalysisResponse() {}

    public Long getId() {
        return id;
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

    public String getSuggestedAction() {
        return suggestedAction;
    }

    public void setSuggestedAction(String suggestedAction) {
        this.suggestedAction = suggestedAction;
    }

    public String getOriginalLog() {
        return originalLog;
    }

    public void setOriginalLog(String originalLog) {
        this.originalLog = originalLog;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
