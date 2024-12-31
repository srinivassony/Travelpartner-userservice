package com.travelpartner.user_service.services;

import java.io.ByteArrayInputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AdminService {

    ResponseEntity<?> getUserRole(HttpServletRequest req, HttpServletResponse res, int page, int size);

    ResponseEntity<?> updateUserById(HttpServletRequest req, HttpServletResponse res, String id,
            UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

    ByteArrayInputStream downloadUsersExcel(HttpServletRequest req, HttpServletResponse res);

    ResponseEntity<?> deleteUser(HttpServletRequest req, HttpServletResponse res, String id);

    // ResponseEntity<?> getUsersWithPagination(HttpServletRequest req,
    // HttpServletResponse res, int page, int size);

    // ResponseEntity<?> uploadUsersData(HttpServletRequest req, HttpServletResponse
    // res, MultipartFile file);

}