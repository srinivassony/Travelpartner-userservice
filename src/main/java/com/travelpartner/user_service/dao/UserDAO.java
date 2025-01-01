package com.travelpartner.user_service.dao;


import java.util.List;
import java.util.Optional;

import com.travelpartner.user_service.dto.UserGalleryDTO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserPostDTO;
import com.travelpartner.user_service.dto.UserProfilePicDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserPostEntity;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.entity.UserGalleryEntity;
import com.travelpartner.user_service.entity.UserProfilePicEntity;

public interface UserDAO {

    UserEntity updateUserInfo(UserServiceDTO userServiceDTO, UserInfoDTO userDetails);

    Optional<UserProfilePicEntity> getProfilePicByUserID(String id);

    Optional<UserEntity> getUserById(String id);

    UserProfilePicDTO createProfilePic(UserProfilePicEntity userProfilePic);

    UserProfilePicDTO updateProfilePic(String fileName, String id, String uuid);

    List<UserGalleryDTO> createUserImages(List<UserGalleryEntity> userGalleryDetails);

    UserServiceDTO getUserInfoById(String id);

    UserPostDTO createUserPost(UserPostEntity setUserPost);
}
