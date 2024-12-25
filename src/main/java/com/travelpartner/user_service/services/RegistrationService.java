package com.travelpartner.user_service.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface RegistrationService {

	ResponseEntity<?> createUserInfo(@Valid UserServiceDTO userServiceDTO, HttpServletRequest req,
			HttpServletResponse res);

	ResponseEntity<?> athunticateUser(UserEntity userEntity, HttpServletRequest req, HttpServletResponse res);

	ResponseEntity<?> onBoardinguserInfo(HttpServletRequest req, HttpServletResponse res, String id);

}
