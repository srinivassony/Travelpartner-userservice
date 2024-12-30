package com.travelpartner.user_service.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserPostDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    ResponseEntity<?> updateUserInfo(UserInfoDTO userDetails, UserServiceDTO userServiceDTO, HttpServletRequest req,
            HttpServletResponse res);

    ResponseEntity<?> uploadUserPic(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails,
            MultipartFile file);

    ResponseEntity<?> uploadMultipleImages(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails,
                    MultipartFile[] files);

    ResponseEntity<?> getUserDetailsById(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails);

    ResponseEntity<?> createUserPost(HttpServletRequest req, HttpServletResponse res, UserPostDTO userPostDTO);

}
