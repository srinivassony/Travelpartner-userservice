package com.travelpartner.user_service.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
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
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2")
public class HomeController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    UserService userService;

    // HttpServletRequest req;
    // UserInfoDTO userDetails = (UserInfoDTO) req.getAttribute("user");
    @GetMapping("/user/dashboard")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String home(HttpServletRequest req,
            HttpServletResponse res) {
        return "Welcome to user dashboard";
    }

    @PostMapping("/user/upload/profilepic")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> onBoardingUser(HttpServletRequest req,
            HttpServletResponse res, @RequestParam("file") MultipartFile file) {
        UserInfoDTO userDetails = (UserInfoDTO) req.getAttribute("user");
        System.out.println(userDetails.getUuid());
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Save the file locally
        Path userUploadPath = Paths.get(uploadDir, userDetails.getUuid());
        System.out.println("Upload directory for user: " + userUploadPath.toString());
        try {
            if (!Files.exists(userUploadPath)) {
                Files.createDirectories(userUploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory: " + userUploadPath, e);
        }
        Path filePath = userUploadPath.resolve(fileName);
        try {
            file.transferTo(filePath.toFile());
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save file: " + file.getOriginalFilename(), e);
        }
        return new ResponseEntity<>("hhh", HttpStatus.INTERNAL_SERVER_ERROR);
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
