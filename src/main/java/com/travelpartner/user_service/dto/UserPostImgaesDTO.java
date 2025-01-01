package com.travelpartner.user_service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travelpartner.user_service.entity.UserPostEntity;

import jakarta.validation.constraints.NotBlank;

public class UserPostImgaesDTO {

    private String id;

    @NotBlank(message="File Name is required")
    private String postFileName;

    @NotBlank(message="File id is required")
    private String postFileId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    private String updatedBy;

	private UserPostEntity userPostImages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostFileName() {
        return postFileName;
    }

    public void setPostFileName(String postFileName) {
        this.postFileName = postFileName;
    }

    public String getPostFileId() {
        return postFileId;
    }

    public void setPostFileId(String postFileId) {
        this.postFileId = postFileId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public UserPostEntity getUserPostImages() {
        return userPostImages;
    }

    public void setUserPostImages(UserPostEntity userPostImages) {
        this.userPostImages = userPostImages;
    }

    
}
