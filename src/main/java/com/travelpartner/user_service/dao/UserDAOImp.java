package com.travelpartner.user_service.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserProfilePicDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.entity.UserProfilePicEntity;
import com.travelpartner.user_service.repository.UserProfilePicRepo;
import com.travelpartner.user_service.repository.UserRepository;
import com.travelpartner.user_service.utill.UtillDTO;

@Service
public class UserDAOImp implements UserDAO {

    @Autowired
    UserRepository jpaUserRepo;

    @Autowired
    UserProfilePicRepo userProfilePicRepo;

    UtillDTO utillDTO;

    @Override
    public UserEntity updateUserInfo(UserServiceDTO userServiceDTO, UserInfoDTO userDetails) {
        // TODO Auto-generated method stub
        return jpaUserRepo.findById(userDetails.getId())
                .map(entity -> {
                    // Update other fields as needed
                    if (userServiceDTO.getUserName() != null ) {
                        entity.setPhone(userServiceDTO.getUserName());
                    }

                    if (userServiceDTO.getEmail() != null ) {
                        entity.setPhone(userServiceDTO.getEmail());
                    }

                    if (userServiceDTO.getPhone() != null ) {
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
    public UserProfilePicEntity createProfilePic(UserProfilePicEntity userProfilePic) {
        return userProfilePicRepo.save(userProfilePic);
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
}

