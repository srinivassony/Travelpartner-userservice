package com.travelpartner.user_service.services;

import java.io.ByteArrayInputStream;

import org.springframework.http.ResponseEntity;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AdminService {

    ResponseEntity<?> getUserRole(HttpServletRequest req, HttpServletResponse res);

    ResponseEntity<?> updateUserById(HttpServletRequest req, HttpServletResponse res, String id,
            UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

    ByteArrayInputStream downloadUsersExcel(HttpServletRequest req, HttpServletResponse res);

}
