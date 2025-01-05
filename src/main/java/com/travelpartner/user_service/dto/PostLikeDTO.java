package com.travelpartner.user_service.dto;

import java.time.LocalDateTime;

import com.travelpartner.user_service.entity.UserPostEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PostLikeDTO {

    private String id;

    private UserPostEntity postLike;

    @NotBlank(message = "Like is required!")
    @NotEmpty(message = "Like filed should not empty.")
    @NotNull(message = "Like filed should not null.")
    private int isLike = 0;

    @NotBlank(message = "User id is required!")
    @NotEmpty(message = "User id filed should not empty.")
    @NotNull(message = "User id filed should not null.")
    private String userId;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private String postId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserPostEntity getPostLike() {
        return postLike;
    }

    public void setPostLike(UserPostEntity postLike) {
        this.postLike = postLike;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

}
