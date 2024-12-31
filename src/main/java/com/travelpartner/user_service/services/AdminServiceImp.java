package com.travelpartner.user_service.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    // @Override
    // public ResponseEntity<?> uploadUsersData(HttpServletRequest req,
    // HttpServletResponse res, MultipartFile file) {
    // // TODO Auto-generated method stub
    // try {

    // InputStream inputStream = file.getInputStream();
    // Workbook workbook = WorkbookFactory.create(inputStream);
    // // XSSFSheet sheetName = (XSSFSheet) workbook.getSheet("USER");
    // Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
    // Iterator<Row> rowIterator = sheet.iterator();

    // List<UserEntity> excelData = new ArrayList<>();

    // // Iterate over the rows and process each row
    // while (rowIterator.hasNext()) {
    // Row row = rowIterator.next();
    // // Skip header row (if any)
    // if (row.getRowNum() == 0)
    // continue;

    // // Read the columns, handle missing values
    // String name = getStringCellValue(row, 0); // First column
    // String email = getStringCellValue(row, 1); // Second column
    // if (email == null || email.isEmpty()) {

    // String errorMessage = "Email is requred for user registration";

    // CustomResponse<String> responseBody = new CustomResponse<>(errorMessage,
    // "NOT_FOUND",
    // HttpStatus.NOT_FOUND.value(), req.getRequestURI(), LocalDateTime.now());

    // return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    // }
    // String phone = getStringCellValue(row, 2); // Third column
    // String role = getStringCellValue(row, 3); // Fourth column
    // String password = getStringCellValue(row, 4); // Assuming 5th column for
    // password (you can modify as
    // // needed)
    // if (password == null || password.isEmpty()) {
    // password = "123"; // Default password
    // }

    // String country = getStringCellValue(row, 5); // Sixth column
    // if (country == null || country.isEmpty()) {
    // country = "India"; // Default country
    // }

    // String uuid = UUID.randomUUID().toString();

    // excelData.addAll(uploadedUserData);

    // }

    // List<UserEntity> uploadedUserData = adminDAO.uploadUserData(excelData);

    // workbook.close();

    // CustomResponse<List<UserEntity>> responseBody = new
    // CustomResponse<>(excelData, "SUCCESS",
    // HttpStatus.OK.value(), req.getRequestURI(), LocalDateTime.now());

    // // Close the workbook after use

    // return new ResponseEntity<>(responseBody, HttpStatus.OK);

    // } catch (Exception e) {

    // String stackTrace = utills.getStackTraceAsString(e);

    // CustomResponse<String> responseBody = new CustomResponse<>(stackTrace,
    // "BAD_REQUEST",
    // HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
    // return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

    // }
    // }

    // private String getStringCellValue(Row row, int cellIndex) {
    // // Get the cell at the specified index from the row
    // Cell cell = row.getCell(cellIndex);

    // // Check if the cell is not null and contains a string
    // if (cell != null && cell.getCellType() == CellType.STRING) {
    // return cell.getStringCellValue(); // Return the string value of the cell
    // }

    // // If cell is null or not a string, return an empty string
    // return "";
    // }

    // public ResponseEntity<?> uploadUsersData(HttpServletRequest req,
    // HttpServletResponse res, MultipartFile file,
    // UserEntity userEntity) {

    // try {

    // // List<List<String>> rows = new ArrayList<>();

    // // Workbook workbook = WorkbookFactory.create(file.getInputStream());
    // // Sheet sheet = workbook.getSheetAt(0);

    // // rows = StreamSupport.stream(sheet.spliterator(), false)
    // // .map(row -> StreamSupport
    // // .stream(row.spliterator(), false)
    // // .map(this::getCellStringValue)
    // // .collect(Collectors.toList()))
    // // .collect(Collectors.toList());

    // // List<UserEntity> excelDataList = rows.stream().map(row -> {
    // // UserEntity excelData = new UserEntity();
    // // excelData.setUserName(row.get(0));
    // // excelData.setEmail(row.get(1));
    // // excelData.setPassword(row.get(2));
    // // return excelData;
    // // }).collect(Collectors.toList());
    // // jpaUserRep.saveAll(excelDataList);

    // if (file.isEmpty()) {
    // return ResponseEntity.badRequest().body("File is empty. Please upload a valid
    // Excel file.");
    // }
    // if (!file.getOriginalFilename().endsWith(".xlsx")) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body("Invalid file format. Please upload an Excel file.");
    // }

    // Workbook workbook = WorkbookFactory.create(file.getInputStream());
    // Sheet sheet = workbook.getSheetAt(0); // Assume data is in the first sheet

    // // for (Row row : sheet) {
    // // if (row.getRowNum() == 0) {
    // // // Skip the header row
    // // continue;
    // // }
    // // }

    // UserEntity excelData = adminDAO.uploadUserInfo(sheet, userEntity);

    // CustomResponse<?> responseBody = new CustomResponse<>(excelData, "SUCCESS",
    // HttpStatus.OK.value(),
    // req.getRequestURI(), LocalDateTime.now());

    // return new ResponseEntity<>(responseBody, HttpStatus.OK);

    // } catch (Exception e) {

    // String stackTrace = utills.getStackTraceAsString(e);

    // CustomResponse<String> responseBody = new CustomResponse<>(stackTrace,
    // "BAD_REQUEST",
    // HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
    // return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

    // }

    // }

}