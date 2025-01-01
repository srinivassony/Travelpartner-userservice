package com.travelpartner.user_service.utill;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.travelpartner.user_service.dto.UserGalleryDTO;
import com.travelpartner.user_service.dto.UserPostDTO;
import com.travelpartner.user_service.dto.UserProfilePicDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.entity.UserGalleryEntity;
import com.travelpartner.user_service.entity.UserPostEntity;
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

    public List<UserGalleryDTO> convertToUsersGalleryListDTO(List<UserGalleryEntity> userGalleryEntites) {
        return userGalleryEntites.stream()
        .map(entity -> new UserGalleryDTO(
                entity.getId(),
                entity.getImageId(),
                entity.getFileName(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy(),
                entity.getUserGallery().getId())).toList();
    }

    public UserServiceDTO convertToUserDTO(UserEntity entity) {
        System.out.println("entity"+" "+entity.getId());
        UserProfilePicDTO userProfilePicDTO = null;
        List<UserGalleryDTO> userGalleryDTOList = new ArrayList<>();

        if (entity.getProfilePic() != null) {
            UserProfilePicEntity userProfilePic = entity.getProfilePic();
            userProfilePicDTO = new UserProfilePicDTO(
                userProfilePic.getProfilePicId(),
                userProfilePic.getProfilePicName(),
                userProfilePic.getPath(),
                userProfilePic.getUser().getId()
            );
        }

        if (entity.getGalleryEntities() != null && !entity.getGalleryEntities().isEmpty()) {
            List<UserGalleryEntity> userGalleryList = entity.getGalleryEntities();

            for (UserGalleryEntity userGallery : userGalleryList) {
                UserGalleryDTO userGalleryDTOInfo = new UserGalleryDTO(
                        userGallery.getId(),
                        userGallery.getImageId(),
                        userGallery.getFileName(),
                        userGallery.getUserGallery().getId()
                       );
                       userGalleryDTOList.add(userGalleryDTOInfo);
            }
        }

        return new UserServiceDTO(
                entity.getCountry(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getDob(),
                entity.getEmail(),
                entity.getGender(),
                entity.getId(),
                entity.getUuid(),
                entity.getPhone(),
                entity.getRole(),
                entity.getState(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy(),
                entity.getUserName(),
                userProfilePicDTO,
                userGalleryDTOList);
    }

    public List<UserServiceDTO> convertToUsersDTOList(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(userEntity -> new UserServiceDTO(
                        userEntity.getCountry(),
                        userEntity.getCreatedAt(),
                        userEntity.getCreatedBy(),
                        userEntity.getDob(),
                        userEntity.getEmail(),
                        userEntity.getGender(),
                        userEntity.getId(),
                        userEntity.getUuid(),
                        userEntity.getPhone(),
                        userEntity.getRole(),
                        userEntity.getState(),
                        userEntity.getUpdatedAt(),
                        userEntity.getUpdatedBy(),
                        userEntity.getUserName()))
                .collect(Collectors.toList());
    }


    public UserPostDTO convertToUserPostDTO(UserPostEntity entity) {
        return new UserPostDTO(
                entity.getId(),
                entity.getLocation(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getUpdatedBy(),
                entity.getUserPost().getId());
    }

}
