package com.travelpartner.user_service.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travelpartner.user_service.dto.UserInfoDTO;
import java.io.IOException;
import java.nio.file.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v2")
public class HomeController {

	@Value("${file.upload-dir}")
    private String uploadDir;

	@GetMapping("/user/dashboard")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String home(HttpServletRequest req,
			HttpServletResponse res) {
		return "Welcome to user dashboard";
	}

	@PostMapping("/user/upload/profilepic")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> onBoardingUser(HttpServletRequest req,
			HttpServletResponse res,@RequestParam("file") MultipartFile file) {
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
	
	
}
