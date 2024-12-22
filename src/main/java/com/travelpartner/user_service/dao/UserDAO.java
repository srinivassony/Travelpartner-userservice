package com.travelpartner.user_service.dao;

import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;

@Repository
public interface UserDAO {

    UserEntity updateUserInfo(UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

}
