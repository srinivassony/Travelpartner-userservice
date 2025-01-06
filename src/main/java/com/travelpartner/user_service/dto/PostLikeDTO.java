package com.travelpartner.user_service.dto;

import java.time.LocalDateTime;

import com.travelpartner.user_service.entity.UserPostEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PostLikeDTO {

    private String id;

    private UserPostEntity postLike;

    @Min(0)
    @Max(1)
    private int isLike = 0;

    @NotBlank(message = "User id is required!")
    @NotEmpty(message = "User id filed should not empty.")
    @NotNull(message = "User id filed should not null.")
    private String userId;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    @NotBlank(message = "Post id is required!")
    @NotEmpty(message = "Post id filed should not empty.")
    @NotNull(message = "Post id filed should not null.")
    private String postId;

    public PostLikeDTO(String id, int isLike, String userId, String postId, LocalDateTime createdAt, String createdBy,
            LocalDateTime updatedAt, String updatedBy) {
        this.id = id;
        this.isLike = isLike;
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

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
