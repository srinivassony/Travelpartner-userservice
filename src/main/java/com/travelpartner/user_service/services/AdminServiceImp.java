package com.travelpartner.user_service.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.config.CustomResponse;
import com.travelpartner.user_service.dao.AdminDAO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.utill.Utills;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;

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

            List<UserServiceDTO> getUserData = adminDAO.getUserRole(role);

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

    @Override
    public ResponseEntity<?> updateUserById(HttpServletRequest req, HttpServletResponse res, String id,
            UserServiceDTO userServiceDTO, UserInfoDTO userDetails) {

        try {

            if (id.isBlank()) {

                String errorMessages = "Id is required!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            UserServiceDTO updateUser = adminDAO.updateUserInfo(id, userServiceDTO, userDetails);

            CustomResponse<?> responseBody = new CustomResponse<>(updateUser, "UPDATED", HttpStatus.OK.value(),
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

    @Override
    public ByteArrayInputStream downloadUsersExcel(HttpServletRequest req, HttpServletResponse res) {
        try {
            // Retrieve the list of users from the database
            List<UserServiceDTO> getUsersList = adminDAO.getUserList();

            // Generate the Excel file and return it as a ByteArrayInputStream
            return generateUserExcel(getUsersList);

        } catch (Exception e) {
            // Log the error and rethrow a custom exception for better debugging
            e.printStackTrace();
            throw new RuntimeException("Failed to generate Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream generateUserExcel(List<UserServiceDTO> users) throws IOException {
        String[] columns = { "ID", "Username", "Email", "Phone", "Role", "Created At" };

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Users");

            // Header row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Data rows
            int rowIdx = 1;
            for (UserServiceDTO user : users) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUserName());
                row.createCell(2).setCellValue(user.getEmail());
                row.createCell(3).setCellValue(user.getPhone());
                row.createCell(4).setCellValue(user.getRole());
                row.createCell(5).setCellValue(user.getCreatedAt().toString());
            }

            // Auto-size columns
            for (int col = 0; col < columns.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private static CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        return style;
    }

}
