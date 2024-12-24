package com.travelpartner.user_service.utill;

import org.springframework.stereotype.Component;

import com.travelpartner.user_service.dto.UserProfilePicDTO;
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
                entity.getUser().getId()
                );
    }
}
