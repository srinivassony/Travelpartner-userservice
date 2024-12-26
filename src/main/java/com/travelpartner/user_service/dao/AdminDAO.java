package com.travelpartner.user_service.dao;

import java.util.List;
import java.util.Optional;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;

public interface AdminDAO {

    List<UserEntity> getUserRole(String role);

    UserServiceDTO updateUserInfo(String id, UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

}
