package com.travelpartner.user_service.services;

import org.springframework.http.ResponseEntity;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    ResponseEntity<?> updateUserInfo(UserInfoDTO userDetails, UserServiceDTO userServiceDTO, HttpServletRequest req,
            HttpServletResponse res);

}
