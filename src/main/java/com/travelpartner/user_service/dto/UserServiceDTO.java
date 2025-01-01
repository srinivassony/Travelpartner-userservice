package com.travelpartner.user_service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserServiceDTO {

    private String id;

    @Size(min = 2, max = 30, message = "Username must be between 2 and 50 characters")
    private String userName;

    @Email(message = "Invalid email format")
    private String email;

    private String password;

    private String role;

    private String uuid;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    private String country;

    private String state;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dob;

    private String gender;

    private LocalDateTime createdAt;

    private String createdBy;

    private Boolean isAdmin;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private UserProfilePicDTO userProfilePicEntity;

    private List<UserGalleryDTO> userGalleryDTOList;

    public UserServiceDTO() {

    }

    public UserServiceDTO(String country, LocalDateTime createdAt, String createdBy, String dob,
            String email, String gender, String id,String uuid, String phone, String role,
            String state, LocalDateTime updatedAt, String updatedBy, String userName) {
        // Initialize fields
        this.country = country;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.uuid = uuid;
        this.phone = phone;
        this.role = role;
        this.state = state;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.userName = userName;
    }

    public UserServiceDTO(String country2, LocalDateTime createdAt2, String createdBy2, String dob2, String email2,
            String gender2, String id2, String uuid2, String phone2, String role2, String state2,
            LocalDateTime updatedAt2, String updatedBy2, String userName2, UserProfilePicDTO userProfilePicDTO,
            List<UserGalleryDTO> userGalleryDTOList) {
        this.country = country2;
        this.createdAt = createdAt2;
        this.createdBy = createdBy2;
        this.dob = dob2;
        this.email = email2;
        this.gender = gender2;
        this.id = id2;
        this.uuid = uuid2;
        this.phone = phone2;
        this.role = role2;
        this.state = state2;
        this.updatedAt = updatedAt2;
        this.updatedBy = updatedBy2;
        this.userName = userName2;
        this.userProfilePicEntity = userProfilePicDTO;
        this.userGalleryDTOList = userGalleryDTOList;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
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

    public UserProfilePicDTO getUserProfilePicEntity() {
        return userProfilePicEntity;
    }

    public void setUserProfilePicEntity(UserProfilePicDTO userProfilePicEntity) {
        this.userProfilePicEntity = userProfilePicEntity;
    }

    public List<UserGalleryDTO> getUserGalleryDTOList() {
        return userGalleryDTOList;
    }

    public void setUserGalleryDTOList(List<UserGalleryDTO> userGalleryDTOList) {
        this.userGalleryDTOList = userGalleryDTOList;
    }
}
