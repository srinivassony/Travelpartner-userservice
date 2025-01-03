package com.travelpartner.user_service.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.travelpartner.user_service.dto.UserGalleryDTO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserPostDTO;
import com.travelpartner.user_service.dto.UserProfilePicDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserGalleryEntity;
import com.travelpartner.user_service.entity.UserPostEntity;
import com.travelpartner.user_service.entity.UserPostImageEntity;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.entity.UserProfilePicEntity;
import com.travelpartner.user_service.utill.Utills;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImp implements UserService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.upload-gallery-dir}")
    private String galleryDir;

    @Autowired
    UserDAO userDAO;

    @Autowired
    Utills utills;

    @Override
    public ResponseEntity<?> updateUserInfo(UserInfoDTO userDetails, UserServiceDTO userServiceDTO,
            HttpServletRequest req,
            HttpServletResponse res) {
        try {
            UserEntity updateUser = userDAO.updateUserInfo(userServiceDTO, userDetails);

            System.out.println("GETTING THE UPDATED USER DETAILS" + " " + updateUser);

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
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            if (file == null || file.isEmpty()) {
                String errorMessage = "No file provided or file is empty.";
                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            if (!fileName.matches(".*\\.(png|jpg|jpeg)$")) {
                String errorMessages = "Invalid file type. Only PNG, JPG, JPEG files are allowed!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            Optional<UserEntity> userInfo = userDAO.getUserById(userDetails.getId());

            if (userInfo.isEmpty()) {
                String errorMessages = "User deatils not found!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            Optional<UserProfilePicEntity> getImageInfo = userDAO.getProfilePicByUserID(userDetails.getId());

            if (getImageInfo.isEmpty()) {
                String profilePicId = UUID.randomUUID().toString();
                System.out.println("82222222" + " " + profilePicId);

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

                UserProfilePicDTO createUserProfilePic = userDAO.createProfilePic(userProfilePic);

                CustomResponse<?> responseBody = new CustomResponse<>(createUserProfilePic, "UPDATED",
                        HttpStatus.OK.value(),
                        req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else {
                // Get the old profile pic file path
                Path userUploadPath = Paths.get(uploadDir, getImageInfo.get().getProfilePicId(),
                        getImageInfo.get().getProfilePicName());
                System.out.println("userUploadPath " + userUploadPath);

                // Check if the old file exists and delete it
                if (Files.exists(userUploadPath) && !Files.isDirectory(userUploadPath)) {
                    Files.delete(userUploadPath);
                    System.out.println("Old file deleted successfully: " + userUploadPath);
                } else if (Files.isDirectory(userUploadPath)) {
                    String errorMessages = "Path is a directory, not a file: " + userUploadPath;

                    CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                            HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                } else {
                    String errorMessages = "File does not exist: " + userUploadPath;

                    CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                            HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
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

    @Override
    public ResponseEntity<?> uploadMultipleImages(HttpServletRequest req, HttpServletResponse res,
            UserInfoDTO userDetails, MultipartFile[] files) {
        try {

            List<UserGalleryEntity> userGalleryDetails = new ArrayList<>();

            if (files == null || files.length == 0) {
                String errorMessage = "No files provided.";
                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            Optional<UserEntity> userInfo = userDAO.getUserById(userDetails.getId());

            if (userInfo.isEmpty()) {
                String errorMessages = "User deatils not found!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            for (MultipartFile file : files) {

                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                if (!fileName.matches(".*\\.(png|jpg|jpeg)$")) {
                    String errorMessages = "Invalid file type. Only PNG, JPG, JPEG files are allowed!";

                    CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                            HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                }

                String imageId = UUID.randomUUID().toString();
                System.out.println("82222222" + " " + imageId);

                Path userUploadPath = Paths.get(galleryDir, imageId);
                System.out.println(userUploadPath);
                Files.createDirectories(userUploadPath);
                Path filePath = userUploadPath.resolve(fileName);
                file.transferTo(filePath.toFile());

                UserGalleryEntity userGalleryEntity = new UserGalleryEntity();

                userGalleryEntity.setImageId(imageId);
                userGalleryEntity.setFileName(fileName);
                userGalleryEntity.setCreatedAt(LocalDateTime.now());
                userGalleryEntity.setCreatedBy(userDetails.getUuid());
                userGalleryEntity.setUserGallery(userInfo.get());

                userGalleryDetails.add(userGalleryEntity);
            }

            List<UserGalleryDTO> createUserGallery = userDAO.createUserImages(userGalleryDetails);

            CustomResponse<?> responseBody = new CustomResponse<>(createUserGallery, "UPDATED",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {
            String stackTrace = utills.getStackTraceAsString(e);

            CustomResponse<String> responseBody = new CustomResponse<>(stackTrace, "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getUserDetailsById(HttpServletRequest req, HttpServletResponse res,
            UserInfoDTO userDetails) {
        try {
            System.out.println("82222222" + " " + userDetails.getId());

            UserServiceDTO getUserDetails = userDAO.getUserInfoById(userDetails.getId());

            if (getUserDetails.getId() == null) {

                String errorMessages = "User deatils not found!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            CustomResponse<?> responseBody = new CustomResponse<>(getUserDetails, "OK",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {

            String stackTrace = utills.getStackTraceAsString(e);

            CustomResponse<String> responseBody = new CustomResponse<>(stackTrace, "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> createUserPostAndImages(HttpServletRequest req, HttpServletResponse res, UserPostDTO userPostDTO, MultipartFile[] files,
            UserInfoDTO userDetails) {
        try {

            List<UserPostImageEntity> userPostImageEntities = new ArrayList<>();

            if (files == null || files.length == 0) {
                String errorMessage = "No files provided.";
                CustomResponse<String> responseBody = new CustomResponse<>(errorMessage, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            Optional<UserEntity> userInfo = userDAO.getUserById(userDetails.getId());

            if (userInfo.isEmpty()) {
                String errorMessages = "User deatils not found!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            UserPostEntity setUserPost = new UserPostEntity();
            setUserPost.setLocation(userPostDTO.getLocation());
            setUserPost.setDescription(userPostDTO.getDescription());
            setUserPost.setCreatedAt(LocalDateTime.now());
            setUserPost.setCreatedBy(userDetails.getUuid());
            setUserPost.setUserPost(userInfo.get());

            for (MultipartFile file : files) {

                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                if (!fileName.matches(".*\\.(png|jpg|jpeg)$")) {
                    String errorMessages = "Invalid file type. Only PNG, JPG, JPEG files are allowed!";

                    CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                            HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                }

                String postFiledId = UUID.randomUUID().toString();
                System.out.println("82222222" + " " + postFiledId);

                Path userUploadPath = Paths.get(galleryDir, postFiledId);
                System.out.println(userUploadPath);
                Files.createDirectories(userUploadPath);
                Path filePath = userUploadPath.resolve(fileName);
                file.transferTo(filePath.toFile());

                UserPostImageEntity setUserPostImageEntity = new UserPostImageEntity();
                setUserPostImageEntity.setPostFileId(postFiledId);
                setUserPostImageEntity.setPostFileName(fileName);
                setUserPostImageEntity.setCreatedAt(LocalDateTime.now());
                setUserPostImageEntity.setCreatedBy(userDetails.getUuid());

                userPostImageEntities.add(setUserPostImageEntity);
            }

            UserPostDTO userPostInfo = userDAO.createUserPostAndImage(setUserPost, userPostImageEntities);

            CustomResponse<?> responseBody = new CustomResponse<>(userPostInfo, "SUCCESS",
                    HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {

            String stackTrace = utills.getStackTraceAsString(e);

            CustomResponse<String> responseBody = new CustomResponse<>(stackTrace, "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

}
