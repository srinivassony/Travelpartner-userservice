package com.travelpartner.user_service.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travelpartner.user_service.config.CustomResponse;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2")
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/user/dashboard")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String home(HttpServletRequest req,
            HttpServletResponse res) {
        return "Welcome to user dashboard";
    }

    @PostMapping("/user/upload/profilepic")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> uploadUserProfile(HttpServletRequest req,
            HttpServletResponse res, @RequestParam("file") MultipartFile file) {
        UserInfoDTO userDetails = (UserInfoDTO) req.getAttribute("user");
        System.out.println(userDetails.getUuid());
        
        return userService.uploadUserPic(req, res,userDetails,file);
    }

    @PostMapping("/user/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateUser(HttpServletRequest req,
            HttpServletResponse res, @Valid @RequestBody UserServiceDTO userServiceDTO, BindingResult result) {
        // Normalize file name
        
        UserInfoDTO userDetails = (UserInfoDTO) req.getAttribute("user");

        if (result.hasErrors()) {

			// Collecting error messages
			StringBuilder errorMessages = new StringBuilder();

			result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("; "));

			System.out.println("errorMessages" + " " + errorMessages);

			CustomResponse<String> responseBody = new CustomResponse<>(errorMessages.toString(), "BAD_REQUEST",
					HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

			return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
		}

        return userService.updateUserInfo(userDetails, userServiceDTO, req, res);
    }

}
