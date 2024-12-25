package com.travelpartner.user_service.dao;

import java.util.Optional;

import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;

public interface RegistrationDAO {

    Optional<UserEntity> isUserExists(String email);

    UserEntity createUser(UserEntity userDetails);

    UserEntity updateUserInfo(String id);

}
