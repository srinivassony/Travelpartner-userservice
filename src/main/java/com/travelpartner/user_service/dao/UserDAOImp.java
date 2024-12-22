package com.travelpartner.user_service.dao;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.repository.UserRepository;

@Service
public class UserDAOImp implements UserDAO {

    @Autowired
    UserRepository jpaUserRepo;

    @Override
    public UserEntity updateUserInfo(UserServiceDTO userServiceDTO, UserInfoDTO userDetails) {
        // TODO Auto-generated method stub
        return jpaUserRepo.findById(userDetails.getId())
                .map(entity -> {
                    // Update other fields as needed
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

}
