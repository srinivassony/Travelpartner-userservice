package com.travelpartner.user_service.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.data.domain.Pageable;

import com.travelpartner.user_service.dao.UserDAO;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    AdminDAO adminDAO;

    @Autowired
    Utills utills;

    @Override
    public ResponseEntity<?> getUserRole(HttpServletRequest req, HttpServletResponse res, int page, int size) {
        try {

            String role = "ROLE_USER,";

            Pageable pageable = PageRequest.of(page, size);

            Page<UserEntity> getUserData = adminDAO.getUserRole(role, pageable);

            Map<String, Object> finalUserList = new LinkedHashMap<>();
            finalUserList.put("users", getUserData.getContent());
            finalUserList.put("currentPage", getUserData.getNumber());
            finalUserList.put("totalItems", getUserData.getTotalElements());
            finalUserList.put("totalPages", getUserData.getTotalPages());

            CustomResponse<?> responseBody = new CustomResponse<>(finalUserList, "SUCCESS",
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
        try {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Sheet sheet = workbook.createSheet("Users");

            String[] columns = { "Username", "Email", "Phone", "Role", "Created At" };

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
                row.createCell(0).setCellValue(user.getUserName());
                row.createCell(1).setCellValue(user.getEmail());
                row.createCell(2).setCellValue(user.getPhone());
                row.createCell(3).setCellValue(user.getRole());
                row.createCell(4).setCellValue(user.getCreatedAt().toString());
            }

            // Auto-size columns
            for (int col = 0; col < columns.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            // Log the error and rethrow a custom exception for better debugging
            e.printStackTrace();
            throw new RuntimeException("Failed to generate Excel file: " + e.getMessage());
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

    @Override
    public ResponseEntity<?> deleteUser(HttpServletRequest req, HttpServletResponse res, String id) {
        // TODO Auto-generated method stub

        try {

            if (id.isBlank()) {

                String errorMessages = "Id is required!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            Optional<UserEntity> deleteUser = adminDAO.deleteUserInfo(id);

            if (deleteUser.isEmpty()) {

                String errorMessage = "User not found with ID: " + id;

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "NOT_FOUND",
                        HttpStatus.NOT_FOUND.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
            }

            CustomResponse<?> responseBody = new CustomResponse<>("User deleted successfully", "DELETED",
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
    public ResponseEntity<?> uploadUsersData(HttpServletRequest req, HttpServletResponse res, MultipartFile file) {

        try {
            if (file.isEmpty()) {

                String errorMessage = "File is Empty !";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "NOT_FOUND",
                        HttpStatus.NOT_FOUND.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);

            }

            if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
                String errorMessage = "Invalid file type! Please upload a valid Excel file.";
                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            List<UserEntity> users = new ArrayList<>();
            List<String> emails = new ArrayList<>();

            try {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);

                validateHeaders(headerRow);

                System.out.println(sheet.getLastRowNum());

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    // Skip rows where any cell has an empty string
                    if (isRowEmpty(row)) {
                        continue;
                    }
                    UserEntity user = validateAndParseRow(row);
                    users.add(user);
                }

                for (int emailIndex = 1; emailIndex <= sheet.getLastRowNum(); emailIndex++) {
                    Row row = sheet.getRow(emailIndex);
                    emails.add(row.getCell(1).toString());
                }

                List<UserEntity> existingUsers = adminDAO.getExisitingUsers(emails);

                if (existingUsers.toArray().length > 0) {

                    List<String> emailList = existingUsers.stream()
                            .map(UserEntity::getEmail) // Extracts only the email field
                            .toList();

                    String emailString = String.join(", ", emailList);

                    String message = "User email already exists: " + emailString;

                    CustomResponse<String> responseBody = new CustomResponse<>(message, "NOT_FOUND",
                            HttpStatus.NOT_FOUND.value(), req.getRequestURI(), LocalDateTime.now());

                    return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
                }

            } catch (Exception e) {

                CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(),
                        "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

            }

            List<UserEntity> userList = adminDAO.uploadUserInfo(users);

            CustomResponse<?> responseBody = new CustomResponse<>(userList, "SUCCESS",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {
            CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(),
                    "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateHeaders(Row headerRow) {
        List<String> requiredHeaders = List.of("username", "email", "phone", "country", "state", "dob");
        for (int i = 0; i < requiredHeaders.size(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !requiredHeaders.get(i).equalsIgnoreCase(cell.getStringCellValue().trim())) {
                throw new IllegalArgumentException("Invalid column name: " + requiredHeaders.get(i));
            }
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String cellValue = cell.toString().trim();
                if (!cellValue.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private UserEntity validateAndParseRow(Row row) {
        UserEntity user = new UserEntity();

        // Assuming the expected columns are in specific positions (adjust as needed)
        // For example, column 0 is "username", column 1 is "email", etc.

        user.setUuid(utills.generateString(36));
        // Read "username" (column 0)
        Cell usernameCell = row.getCell(0);
        if (usernameCell != null && usernameCell.getCellType() == CellType.STRING) {
            user.setUserName(usernameCell.getStringCellValue().trim());
        } else {
            // Handle error if the value is not a string or is empty
            throw new IllegalArgumentException("Please enter the field username at " + (row.getRowNum() + 1));
        }

        // Read "email" (column 1)
        Cell emailCell = row.getCell(1);
        if (emailCell != null && emailCell.getCellType() == CellType.STRING) {
            user.setEmail(emailCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field email at " + (row.getRowNum() + 1));
        }

        // Read "phone" (column 2)
        Cell phoneCell = row.getCell(2);
        if (phoneCell != null) {
            if (phoneCell.getCellType() == CellType.NUMERIC) {
                // Convert numeric phone number to string
                String phone = String.valueOf((long) phoneCell.getNumericCellValue());

                // Validate phone number format
                if (phone.matches("\\d{10}")) {
                    user.setPhone(phone);
                } else {
                    throw new IllegalArgumentException(
                            "Invalid phone number. Please enter a valid 10-digit phone number at" + (row.getRowNum() + 1));
                }
            } else {
                throw new IllegalArgumentException(
                        "Invalid phone cell type. Phone number must be a string or numeric at"+ (row.getRowNum() + 1));
            }
        } else {
            throw new IllegalArgumentException("Phone field is missing. Please enter the phone number at "+ (row.getRowNum() + 1));
        }

        // Read "country" (column 3)
        Cell countryCell = row.getCell(3);
        if (countryCell != null && countryCell.getCellType() == CellType.STRING) {
            user.setCountry(countryCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field country at"+ (row.getRowNum() + 1));
        }

        // Read "state" (column 4)
        Cell stateCell = row.getCell(4);
        if (stateCell != null && stateCell.getCellType() == CellType.STRING) {
            user.setState(stateCell.getStringCellValue().trim());
        } else {
            throw new IllegalArgumentException("Please enter the field state at"+ (row.getRowNum() + 1));
        }
        // Read "dob" (column 5), assuming it's a date (you can adjust for other
        // formats)
        Cell dobCell = row.getCell(5);
        if (dobCell != null) {
            if (dobCell.getCellType() == CellType.NUMERIC) {
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(dobCell)) {
                    // Convert date to string in desired format
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    user.setDob(sdf.format(dobCell.getDateCellValue())); // Convert date to string
                } else {
                    throw new IllegalArgumentException("Please enter the Valid dob at"+ (row.getRowNum() + 1));
                }
            } else {
                throw new IllegalArgumentException("DOB number should be in " +
                        dobCell.getCellType()+ (row.getRowNum() + 1));
            }
        } else {
            throw new IllegalArgumentException("Please enter the field dob at "+ (row.getRowNum() + 1));
        }

        user.setRole("ROLE_USER,");

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(generateRandomPassword()); // Set a random password if it's not provided
        }
        System.out.println(user.getUserName());
        return user;
    }

    public String generateRandomPassword() {
        int length = 6; // You can change the length as needed
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();

    }
}