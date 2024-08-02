package com.ctw.workstation.shared.attributes;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
public class SharedAttributes {
    @Column(nullable = false, name = "created_at", columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT now()")
    private LocalDateTime modifiedAt;



    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }



}
