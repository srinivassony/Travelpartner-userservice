package com.travelpartner.user_service.dto;

import java.time.LocalDateTime;

import com.travelpartner.user_service.entity.UserEntity;

public class UserGalleryDTO {
    
    private String id;
	
	private String imageId;
	
	private String fileName;
	
	private LocalDateTime createdAt;

	private String createdBy;
	
	private LocalDateTime updatedAt;
	
	private String updatedBy;
    
	private UserEntity userGallery;

    private String userId;

    public UserGalleryDTO(String id, String imageId, String fileName, LocalDateTime createdAt, String createdBy,
            LocalDateTime updatedAt, String updatedBy, String userId) {
        this.id = id;
        this.imageId = imageId;
        this.fileName = fileName;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.userId = userId;
    }

    public UserGalleryDTO(String id2, String imageId2, String fileName2, String userId2) {
        this.id = id2;
        this.imageId = imageId2;
        this.fileName = fileName2;
        this.userId = userId2;
    }

    public UserGalleryDTO() {
       
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public UserEntity getUserGallery() {
        return userGallery;
    }

    public void setUserGallery(UserEntity userGallery) {
        this.userGallery = userGallery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
