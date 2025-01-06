package com.travelpartner.user_service.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.travelpartner.user_service.dto.PostCommentDTO;
import com.travelpartner.user_service.dto.PostLikeDTO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserPostDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface UserService {

        ResponseEntity<?> updateUserInfo(UserInfoDTO userDetails, UserServiceDTO userServiceDTO, HttpServletRequest req,
                        HttpServletResponse res);

        ResponseEntity<?> uploadUserPic(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails,
                        MultipartFile file);

        ResponseEntity<?> uploadMultipleImages(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails,
                        MultipartFile[] files);

        ResponseEntity<?> getUserDetailsById(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails);

        ResponseEntity<?> createUserPostAndImages(HttpServletRequest req, HttpServletResponse res,
                        UserPostDTO userPostDTO, MultipartFile[] files, UserInfoDTO userDetails);

        ResponseEntity<?> createPostLike(HttpServletRequest req, HttpServletResponse res, PostLikeDTO postLikeDTO,
                        UserInfoDTO userDetails);

        ResponseEntity<?> createPostComment(HttpServletRequest req, HttpServletResponse res,
                PostCommentDTO postCommentDTO, UserInfoDTO userDetails);

        ResponseEntity<?> FetchUserPosts(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails);

}
