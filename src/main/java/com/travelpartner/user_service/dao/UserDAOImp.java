package com.travelpartner.user_service.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.dto.PostCommentDTO;
import com.travelpartner.user_service.dto.PostLikeDTO;
import com.travelpartner.user_service.dto.UserGalleryDTO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserPostDTO;
import com.travelpartner.user_service.dto.UserPostsViewDTO;
import com.travelpartner.user_service.dto.UserProfilePicDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.PostCommentEntity;
import com.travelpartner.user_service.entity.PostLikeEntity;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.entity.UserGalleryEntity;
import com.travelpartner.user_service.entity.UserPostEntity;
import com.travelpartner.user_service.entity.UserPostImageEntity;
import com.travelpartner.user_service.entity.UserProfilePicEntity;
import com.travelpartner.user_service.repository.PostCommentRepo;
import com.travelpartner.user_service.repository.PostLikeRepo;
import com.travelpartner.user_service.repository.UserGalleryRepo;
import com.travelpartner.user_service.repository.UserPostImagesRepo;
import com.travelpartner.user_service.repository.UserPostRepo;
import com.travelpartner.user_service.repository.UserProfilePicRepo;
import com.travelpartner.user_service.repository.UserRepository;
import com.travelpartner.user_service.utill.UtillDTO;
import com.travelpartner.user_service.dto.UserPostsViewDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserDAOImp implements UserDAO {

    @Autowired
    UserRepository jpaUserRepo;

    @Autowired
    UserProfilePicRepo userProfilePicRepo;

    @Autowired
    UserGalleryRepo userGalleryRepo;

    @Autowired
    UserPostRepo userPostRepo;

    @Autowired
    UserPostImagesRepo userPostImagesRepo;

    @Autowired
    PostLikeRepo postLikeRepo;

    @Autowired
    PostCommentRepo postCommentRepo;

    @Autowired
    UtillDTO utillDTO;

    @Autowired
    private EntityManager entityManager;

    @Override
    public UserEntity updateUserInfo(UserServiceDTO userServiceDTO, UserInfoDTO userDetails) {
        // TODO Auto-generated method stub
        return jpaUserRepo.findById(userDetails.getId())
                .map(entity -> {
                    // Update other fields as needed
                    if (userServiceDTO.getUserName() != null) {
                        entity.setPhone(userServiceDTO.getUserName());
                    }

                    if (userServiceDTO.getEmail() != null) {
                        entity.setPhone(userServiceDTO.getEmail());
                    }

                    if (userServiceDTO.getPhone() != null) {
                        entity.setPhone(userServiceDTO.getPhone());
                    }

                    if (userServiceDTO.getCountry() != null) {
                        entity.setCountry(userServiceDTO.getCountry());
                    }

                    if (userServiceDTO.getState() != null) {
                        entity.setState(userServiceDTO.getState());
                    }

                    if (userServiceDTO.getDob() != null) {
                        entity.setDob(userServiceDTO.getDob());
                    }

                    if (userServiceDTO.getGender() != null) {
                        entity.setGender(userServiceDTO.getGender());
                    }

                    entity.setUpdatedAt(LocalDateTime.now());
                    entity.setUpdatedBy(userDetails.getUuid());

                    return jpaUserRepo.save(entity);

                }).orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userDetails.getId()));
    }

    @Override
    public Optional<UserProfilePicEntity> getProfilePicByUserID(String id) {
        return userProfilePicRepo.findByUserId(id);
    }

    @Override
    public Optional<UserEntity> getUserById(String id) {
        return jpaUserRepo.findById(id);
    }

    @Override
    public UserProfilePicDTO createProfilePic(UserProfilePicEntity userProfilePic) {
        UserProfilePicEntity userProfilePicEntity = userProfilePicRepo.save(userProfilePic);
        return utillDTO.convertToUserProfileDTO(userProfilePicEntity);
    }

    @Override
    public UserProfilePicDTO updateProfilePic(String fileName, String id, String uuid) {
        return userProfilePicRepo.findById(id).map(entity -> {
            // Update other fields as needed
            entity.setProfilePicName(fileName);
            entity.setUpdatedAt(LocalDateTime.now());
            entity.setUpdatedBy(uuid);
            UserProfilePicEntity userProfilePicEntity = userProfilePicRepo.save(entity);
            // Convert entity to DTO and return
            return utillDTO.convertToUserProfileDTO(userProfilePicEntity);
        }).orElseThrow(() -> new UsernameNotFoundException("User profile pic is not updated for this id: " + id));
    }

    @Override
    public List<UserGalleryDTO> createUserImages(List<UserGalleryEntity> userGalleryDetails) {
        List<UserGalleryEntity> galleryEntity = userGalleryRepo.saveAll(userGalleryDetails);
        return utillDTO.convertToUsersGalleryListDTO(galleryEntity);
    }

    @Override
    public UserServiceDTO getUserInfoById(String id) {

        UserEntity userEntity = jpaUserRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found."));

        return utillDTO.convertToUserDTO(userEntity);
    }

    @Override
    @Transactional
    public UserPostDTO createUserPostAndImage(UserPostEntity setUserPost,
            List<UserPostImageEntity> userPostImageEntities) {

        UserPostEntity userPostEntity = userPostRepo.save(setUserPost);

        List<UserPostImageEntity> userPostImageList = new ArrayList<>();
        for (UserPostImageEntity image : userPostImageEntities) {
            image.setUserPostImages(userPostEntity);// Set the post for the image
            UserPostImageEntity userPostImageEntity = userPostImagesRepo.save(image);
            userPostImageList.add(userPostImageEntity);
        }

        userPostEntity.setUserPostImageEntities(userPostImageList);

        return utillDTO.convertToUserPostDTO(userPostEntity);
    }

    @Override
    public Optional<PostLikeEntity> getPostLikeByIdAndUserId(String postId, String userId) {
        return postLikeRepo.findByUserIdAndPostLike_Id(userId, postId);
    }

    @Override
    public Optional<UserPostEntity> getUserPostById(String postId) {
        return userPostRepo.findById(postId);
    }

    @Override
    public PostLikeDTO createPostLike(PostLikeEntity postLikeEntity) {
        PostLikeEntity postLikeEntityInfo =  postLikeRepo.save(postLikeEntity);
        return utillDTO.convertToPostLikeDTO(postLikeEntityInfo);
    }

    @Override
    public PostLikeDTO updatePostLikeById(PostLikeEntity entity) {
        PostLikeEntity postLikeEntityInfo =  postLikeRepo.save(entity);
        return utillDTO.convertToPostLikeDTO(postLikeEntityInfo);
    }

    @Override
    public PostCommentDTO createPostComment(PostCommentEntity postCommentEntity) {
        PostCommentEntity entity = postCommentRepo.save(postCommentEntity);
        return utillDTO.convertToPostCommentDTO(entity);
    }

    @Override
    public List<UserPostsViewDTO> getUserPostsByUserIdList(String userId) {
        List<UserPostsViewDTO> userPostsList = new ArrayList<>();

        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try {
                CallableStatement callableStatement = connection.prepareCall("{ ? = call rp_function_fetch_users_posts_v1(?) }");
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.setString(2, userId);
                callableStatement.execute();

                try {
                    ResultSet resultSet =  (ResultSet) callableStatement.getObject(1);
                    while (resultSet.next()) {
                        UserPostsViewDTO userPost = new UserPostsViewDTO(
                                resultSet.getString("id"),
                                resultSet.getString("userName"),
                                resultSet.getString("location"),
                                resultSet.getString("description"),
                                resultSet.getString("profilePicId"),
                                resultSet.getString("profilePicName"),
                                resultSet.getString("userId"),
                                resultSet.getString("postImages"),
                                resultSet.getInt("likesCount"),
                                resultSet.getInt("commentsCount")
                        );
                        userPostsList.add(userPost);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        return userPostsList;
    }
}
