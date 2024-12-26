package com.travelpartner.user_service.utill;

import org.springframework.stereotype.Component;

import com.travelpartner.user_service.dto.UserGalleryDTO;
import com.travelpartner.user_service.dto.UserProfilePicDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.entity.UserGalleryEntity;
import com.travelpartner.user_service.entity.UserProfilePicEntity;

@Component
public class UtillDTO {

    public UserProfilePicDTO convertToUserProfileDTO(UserProfilePicEntity entity) {
        return new UserProfilePicDTO(
                entity.getId(),
                entity.getProfilePicId(),
                entity.getProfilePicName(),
                entity.getPath(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy(),
                entity.getUser().getId());
    }

    public UserGalleryDTO convertToUserGalleryDTO(UserGalleryEntity entity) {
        return new UserGalleryDTO(
                entity.getId(),
                entity.getImageId(),
                entity.getFileName(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy(),
                entity.getUserGallery().getId());
    }

    public UserServiceDTO convertToUserDTO(UserEntity entity) {
        return new UserServiceDTO(
                entity.getCountry(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getDob(),
                entity.getEmail(),
                entity.getGender(),
                entity.getId(),
                entity.getPassword(),
                entity.getPhone(),
                entity.getRole(),
                entity.getState(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy(),
                entity.getUserName());

    }
}
