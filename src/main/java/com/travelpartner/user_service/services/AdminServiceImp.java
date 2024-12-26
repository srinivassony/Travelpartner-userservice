package com.travelpartner.user_service.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.config.CustomResponse;
import com.travelpartner.user_service.dao.AdminDAO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.utill.Utills;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    AdminDAO adminDAO;

    @Autowired
    Utills utills;

    @Override
    public ResponseEntity<?> getUserRole(HttpServletRequest req, HttpServletResponse res) {
        try {

            String role = "ROLE_USER,";

            List<UserEntity> getUserData = adminDAO.getUserRole(role);

            CustomResponse<?> responseBody = new CustomResponse<>(getUserData, "UPDATED",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {

            String stackTrace = utills.getStackTraceAsString(e);

            CustomResponse<String> responseBody = new CustomResponse<>(stackTrace,
                    "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

        }

    }
}
