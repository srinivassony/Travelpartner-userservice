package com.travelpartner.user_service.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.config.CustomResponse;
import com.travelpartner.user_service.dao.UserDAO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public ResponseEntity<?> updateUserInfo(UserInfoDTO userDetails, UserServiceDTO userServiceDTO, HttpServletRequest req,
            HttpServletResponse res) {
        // TODO Auto-generated method stub
        try {

            UserEntity updateUser = userDAO.updateUserInfo(userServiceDTO, userDetails);

            System.out.println("GETTING THE UPDATED USER DETAILS" + " " + updateUser );

            CustomResponse<?> responseBody = new CustomResponse<>(updateUser, "UPDATED", HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {

            CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(), "ERROR",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}
