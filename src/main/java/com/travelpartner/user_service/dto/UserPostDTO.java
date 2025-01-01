package com.travelpartner.user_service.dto;

import java.time.LocalDateTime;

import com.travelpartner.user_service.entity.UserEntity;

import jakarta.validation.constraints.NotBlank;

public class UserPostDTO {

    private String id;
    
    @NotBlank(message="Location is required")
    private String location;

    @NotBlank(message="Description is required")
    private String description;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private UserEntity userPost;

    private String userId;

    public UserPostDTO() {
    }

    public UserPostDTO(String id, String location, String description, LocalDateTime createdAt, String createdBy,
            LocalDateTime updatedAt, String updatedBy, String userId) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt=updatedAt;
        this.updatedBy=updatedBy;
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public UserEntity getUserPost() {
        return userPost;
    }

    public void setUserPost(UserEntity userPost) {
        this.userPost = userPost;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    
}
