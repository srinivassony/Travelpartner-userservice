package com.travelpartner.user_service.entity;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tp_user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private String id;

	@NotBlank(message = "Username is required!")
	@Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
	@Column(name = "USER_NAME", nullable = false)
	private String userName;

	@Column(name = "EMAIL", unique = true, nullable = false)
	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Password is required!")
	@NotNull(message = "Password must not be null")
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

	public UserEntity() {

	}

	public UserEntity(String userName, String email, String role, String password, String uuid, LocalDateTime createdAt,
			String createdBy) {
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
}
