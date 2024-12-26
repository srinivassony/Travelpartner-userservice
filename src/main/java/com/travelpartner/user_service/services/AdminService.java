package com.travelpartner.user_service.services;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AdminService {

    ResponseEntity<?> getUserRole(HttpServletRequest req, HttpServletResponse res);

}
