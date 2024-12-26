package com.travelpartner.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelpartner.user_service.services.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v3")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String home(HttpServletRequest req,
            HttpServletResponse res) {
        return "Welcome to admin dashboard";
    }

    @GetMapping("/get/user/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserByRoles(HttpServletRequest req,
            HttpServletResponse res) {

        return adminService.getUserRole(req, res);

    }

}
