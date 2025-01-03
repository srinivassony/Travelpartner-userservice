package com.travelpartner.user_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tp_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Column(name = "UUID", nullable = false)
    private String uuid;

    @Column(name = "CREATED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "IS_REGISTERED")
    private Integer isRegistered = 0;

    @Column(name = "INVITE_ON")
    private String inviteOn;

    @Column(name = "UPDATED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DOB")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dob;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "STATE")
    private String state;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "IS_INVITED")
    private int isInvited = 0;

    @Column(name = "LOGIN")
    private int login = 0;

    @Column(name = "LOGIN_UPDATED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime loginUpdatedAt;

    @Column(name = "LOGOUT")
    private int logout = 0;

    @Column(name = "LOGOUT_UPDATED_AT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime logoutUpdatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfilePicEntity profilePic;

    @OneToMany(mappedBy = "userGallery", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGalleryEntity> galleryEntities;

    @OneToMany(mappedBy = "userPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserPostEntity> userPosts;

    public UserEntity() {

    }

    public UserEntity(String id, String userName, String email, String role, String password, String uuid,
            LocalDateTime createdAt,
            String createdBy) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.uuid = uuid;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        // Initialize other fields if necessary
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getInviteOn() {
        return inviteOn;
    }

    public void setInviteOn(String inviteOn) {
        this.inviteOn = inviteOn;
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

    public Integer getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Integer isRegistered) {
        this.isRegistered = isRegistered;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getIsInvited() {
        return isInvited;
    }

    public void setIsInvited(int isInvited) {
        this.isInvited = isInvited;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public LocalDateTime getLoginUpdatedAt() {
        return loginUpdatedAt;
    }

    public void setLoginUpdatedAt(LocalDateTime loginUpdatedAt) {
        this.loginUpdatedAt = loginUpdatedAt;
    }

    public int getLogout() {
        return logout;
    }

    public void setLogout(int logout) {
        this.logout = logout;
    }

    public LocalDateTime getLogoutUpdatedAt() {
        return logoutUpdatedAt;
    }

    public void setLogoutUpdatedAt(LocalDateTime logoutUpdatedAt) {
        this.logoutUpdatedAt = logoutUpdatedAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserProfilePicEntity getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(UserProfilePicEntity profilePic) {
        this.profilePic = profilePic;
    }

    public List<UserGalleryEntity> getGalleryEntities() {
        return galleryEntities;
    }

    public void setGalleryEntities(List<UserGalleryEntity> galleryEntities) {
        this.galleryEntities = galleryEntities;
    }

    public List<UserPostEntity> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<UserPostEntity> userPosts) {
        this.userPosts = userPosts;
    }

}
