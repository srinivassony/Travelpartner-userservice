package com.travelpartner.user_service.dao;

import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Sheet;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;

public interface AdminDAO {

    org.springframework.data.domain.Page<UserEntity> getUserRole(String role, Pageable pageable);

    UserServiceDTO updateUserInfo(String id, UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

    List<UserServiceDTO> getUserList();

    Optional<UserEntity> deleteUserInfo(String id);

    // public List<UserEntity> uploadUserData(String name, String email, String
    // phone, String role, String password,
    // String country, String uuid);

    Optional<UserEntity> getUserInfoById(String id);

}