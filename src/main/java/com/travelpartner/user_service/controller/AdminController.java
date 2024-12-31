package com.travelpartner.user_service.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
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
            HttpServletResponse res, @RequestParam int page, @RequestParam int size) {

        return adminService.getUserRole(req, res, page, size);
    }

    @PostMapping("/update/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUserById(HttpServletRequest req,
            HttpServletResponse res, @PathVariable("id") String id, @RequestBody UserServiceDTO userServiceDTO) {
        UserInfoDTO userDetails = (UserInfoDTO) req.getAttribute("user");
        return adminService.updateUserById(req, res, id, userServiceDTO, userDetails);
    }

    @GetMapping("/download/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<InputStreamResource> downloadUsersExcel(HttpServletRequest req) {
        try {
            // Call the service method to generate the Excel file
            ByteArrayInputStream excelStream = adminService.downloadUsersExcel(req, null);

            if (excelStream == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(null); // Handle cases where no data is available
            }

            // Set the response headers for the file download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // Ensure proper MIME type for downloads
                    .body(new InputStreamResource(excelStream));

        } catch (Exception e) {
            e.printStackTrace();
            // Provide a more detailed error message in the response
            String errorMessage = "Error occurred while generating the Excel file: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new InputStreamResource(new ByteArrayInputStream(errorMessage.getBytes())));
        }
    }

    @DeleteMapping("/delete/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(HttpServletRequest req, HttpServletResponse res,
            @PathVariable("id") String id) {

        return adminService.deleteUser(req, res, id);
    }

    // @GetMapping("/get/users")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // public ResponseEntity<?> getUsersWithPagination(HttpServletRequest req,
    // HttpServletResponse res,
    // @RequestParam int page, @RequestParam int size) {

    // return adminService.getUsersWithPagination(req, res, page, size);
    // }

    // @PostMapping("/upload/bluck/users")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // public ResponseEntity<?> uploadUserData(HttpServletRequest req,
    // HttpServletResponse res,
    // @RequestParam("file") MultipartFile file, UserServiceDTO userServiceDTO) {

    // return adminService.uploadUsersData(req, res, file);
    // }
}