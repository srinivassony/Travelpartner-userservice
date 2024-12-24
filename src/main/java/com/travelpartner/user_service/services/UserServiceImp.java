package com.travelpartner.user_service.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.travelpartner.user_service.config.CustomResponse;
import com.travelpartner.user_service.dao.UserDAO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserProfilePicDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.entity.UserProfilePicEntity;
import com.travelpartner.user_service.utill.Utills;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImp implements UserService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    UserDAO userDAO;

    @Autowired
    Utills utills;

    @Override
    public ResponseEntity<?> updateUserInfo(UserInfoDTO userDetails, UserServiceDTO userServiceDTO, HttpServletRequest req,
            HttpServletResponse res) {
        try {

            UserEntity updateUser = userDAO.updateUserInfo(userServiceDTO, userDetails);

            System.out.println("GETTING THE UPDATED USER DETAILS" + " " + updateUser );

            CustomResponse<?> responseBody = new CustomResponse<>(updateUser, "UPDATED", HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {

            CustomResponse<String> responseBody = new CustomResponse<>(e.getMessage(), "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> uploadUserPic(HttpServletRequest req, HttpServletResponse res, UserInfoDTO userDetails,
            MultipartFile file) {
        try {

            Optional<UserEntity> userInfo = userDAO.getUserById(userDetails.getId());

            if(userInfo.isEmpty())
            {
                String errorMessages = "User deatils not found!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            Optional<UserProfilePicEntity> getImageInfo = userDAO.getProfilePicByUserID(userDetails.getId());

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            if (getImageInfo.isEmpty()) {
                String profilePicId =  UUID.randomUUID().toString();
                System.out.println("82222222"+" "+profilePicId);

                Path userUploadPath = Paths.get(uploadDir, profilePicId);
                System.out.println(userUploadPath);
                if (!Files.exists(userUploadPath)) {
                    Files.createDirectories(userUploadPath);
                }

                Path filePath = userUploadPath.resolve(fileName);
                file.transferTo(filePath.toFile());

                UserProfilePicEntity userProfilePic = new UserProfilePicEntity();

                userProfilePic.setProfilePicId(profilePicId);
                userProfilePic.setProfilePicName(fileName);
                userProfilePic.setCreatedAt(LocalDateTime.now());
                userProfilePic.setCreatedBy(userDetails.getUuid());
                userProfilePic.setPath(userUploadPath.toString());
                userProfilePic.setUser(userInfo.get());

                UserProfilePicEntity createUserProfilePic = userDAO.createProfilePic(userProfilePic);

                CustomResponse<?> responseBody = new CustomResponse<>(createUserProfilePic, "UPDATED",
                        HttpStatus.OK.value(),
                        req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }
            else {

                // Get the old profile pic file path
                Path userUploadPath = Paths.get(uploadDir, getImageInfo.get().getProfilePicId(),getImageInfo.get().getProfilePicName());
                System.out.println("userUploadPath " + userUploadPath);

                // Check if the old file exists and delete it
                if (Files.exists(userUploadPath) && !Files.isDirectory(userUploadPath)) {
                    Files.delete(userUploadPath);
                    System.out.println("Old file deleted successfully: " + userUploadPath);
                } else if (Files.isDirectory(userUploadPath)) {
                    System.out.println("Path is a directory, not a file: " + userUploadPath);
                } else {
                    System.out.println("File does not exist: " + userUploadPath);
                }

                // Resolve the new file path
                String newFileName = file.getOriginalFilename(); // Assuming fileName comes from the MultipartFile
                Path newFilePath = Paths.get(uploadDir, getImageInfo.get().getProfilePicId(), newFileName);

                // Print the new file path (for debugging)
                System.out.println("New file path: " + newFilePath);

                // Transfer the new file to the resolved path
                file.transferTo(newFilePath.toFile());

                UserProfilePicDTO updateUserProfilePic = userDAO.updateProfilePic(newFileName,
                        getImageInfo.get().getId(), userInfo.get().getUuid());

                CustomResponse<?> responseBody = new CustomResponse<>(updateUserProfilePic, "UPDATED",
                        HttpStatus.OK.value(),
                        req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }

        } catch (Exception e) {

            String stackTrace = utills.getStackTraceAsString(e); 

            CustomResponse<String> responseBody = new CustomResponse<>(stackTrace, "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }
}
