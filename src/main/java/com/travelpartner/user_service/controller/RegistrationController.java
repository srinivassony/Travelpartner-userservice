package com.travelpartner.user_service.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelpartner.user_service.config.CustomResponse;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.services.RegistrationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	@PostMapping("/add/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity userEntity, BindingResult result,
			HttpServletRequest req, HttpServletResponse res) {

		if (result.hasErrors()) {

			// Collecting error messages
			StringBuilder errorMessages = new StringBuilder();

			result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("; "));

			System.out.println("errorMessages" + " " + errorMessages);

			CustomResponse<String> responseBody = new CustomResponse<>(errorMessages.toString(), "BAD_REQUEST",
					HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

			return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
		}

		return registrationService.createUserInfo(userEntity, req, res);
	}

	@PostMapping("/user/authenticate")
	public ResponseEntity<?> login(@RequestBody UserEntity userEntity, HttpServletRequest req,
			HttpServletResponse res) {

		System.out.println("email" + " " + userEntity.getEmail());

		return registrationService.athunticateUser(userEntity, req, res);
	}
	
	

}
