package com.travelpartner.user_service.dao;

import java.util.List;
import java.util.Optional;

import com.travelpartner.user_service.dto.PostCommentDTO;
import com.travelpartner.user_service.dto.PostLikeDTO;
import com.travelpartner.user_service.dto.UserGalleryDTO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserPostDTO;
import com.travelpartner.user_service.dto.UserPostsViewDTO;
import com.travelpartner.user_service.dto.UserProfilePicDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserPostEntity;
import com.travelpartner.user_service.entity.UserPostImageEntity;
import com.travelpartner.user_service.entity.PostCommentEntity;
import com.travelpartner.user_service.entity.PostLikeEntity;
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

    UserPostDTO createUserPostAndImage(UserPostEntity setUserPost, List<UserPostImageEntity> userPostImageEntities);

    Optional<PostLikeEntity> getPostLikeByIdAndUserId(String postId, String userId);

    Optional<UserPostEntity> getUserPostById(String postId);

    PostLikeDTO createPostLike(PostLikeEntity postLikeEntity);

    PostLikeDTO updatePostLikeById(PostLikeEntity entity);

    PostCommentDTO createPostComment(PostCommentEntity postCommentEntity);

    List<UserPostsViewDTO> getUserPostsByUserIdList(String userId);

    List<UserPostsViewDTO> getUserPostsList(String searchKey);

}
