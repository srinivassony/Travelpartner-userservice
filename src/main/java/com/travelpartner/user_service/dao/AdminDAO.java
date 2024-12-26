package com.travelpartner.user_service.dao;

import java.util.List;
import java.util.Optional;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;

public interface AdminDAO {

    List<UserServiceDTO> getUserRole(String role);

    UserServiceDTO updateUserInfo(String id, UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

    List<UserServiceDTO> getUserList();

}
