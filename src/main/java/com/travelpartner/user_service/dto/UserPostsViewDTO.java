package com.travelpartner.user_service.dto;

import java.util.List;

public class UserPostsViewDTO {

    private String id;
    private String userName;
    private String location;
    private String description;
    private String profilePicId;
    private String profilePicName;
    private String userId;
    private List<PostImageDTO> postImages; // Will store JSON array as a String
    private int likesCount;
    private int commentsCount;
    private int totalCount;

    public UserPostsViewDTO() {
    }

    public UserPostsViewDTO(String id, String userName, String location, String description,
            String profilePicId, String profilePicName, String userId,
            List<PostImageDTO> postImages, int likesCount, int commentsCount, int totalCount) {
        this.id = id;
        this.userName = userName;
        this.location = location;
        this.description = description;
        this.profilePicId = profilePicId;
        this.profilePicName = profilePicName;
        this.userId = userId;
        this.postImages = postImages;
        this.likesCount = likesCount;
        this.commentsCount = commentsCount;
        this.totalCount = totalCount;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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
    public String getProfilePicId() {
        return profilePicId;
    }
    public void setProfilePicId(String profilePicId) {
        this.profilePicId = profilePicId;
    }
    public String getProfilePicName() {
        return profilePicName;
    }
    public void setProfilePicName(String profilePicName) {
        this.profilePicName = profilePicName;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<PostImageDTO> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<PostImageDTO> postImages) {
        this.postImages = postImages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
   
}
