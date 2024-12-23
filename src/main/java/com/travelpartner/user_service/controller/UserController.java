package com.travelpartner.user_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v2")
public class UserController {

	@GetMapping("/user/dashboard")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String home(HttpServletRequest req,
			HttpServletResponse res) {
		return "Welcome to user dashboard";
	}

	// @PostMapping("/user/upload/profilepic")
	// @PreAuthorize("hasRole('ROLE_USER')")
	// public ResponseEntity<?> onBoardingUser(HttpServletRequest req,
	// 		HttpServletResponse res,@RequestParam("file") MultipartFile file) {
	// 	UserInfoDTO userDetails = (UserInfoDTO) req.getAttribute("user");
		
	// 	return userService.UploadImage(req, res, userDetails, file);
	// }
	
	
}
