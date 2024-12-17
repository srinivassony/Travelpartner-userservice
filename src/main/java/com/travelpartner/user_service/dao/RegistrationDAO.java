package com.travelpartner.user_service.dao;

import java.util.Optional;

import com.travelpartner.user_service.entity.UserEntity;

import jakarta.validation.Valid;

public interface RegistrationDAO {

	Optional<UserEntity> isUserExists(String email);

	UserEntity createUser(UserEntity userDetails);

	

}
