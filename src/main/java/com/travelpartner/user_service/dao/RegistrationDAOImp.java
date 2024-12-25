package com.travelpartner.user_service.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.repository.UserRepository;
import com.travelpartner.user_service.utill.Utills;

@Service
public class RegistrationDAOImp implements RegistrationDAO {

    @Autowired
    UserRepository jpaUserRepo;

    @Autowired
    Utills utill;

    @Override
    public Optional<UserEntity> isUserExists(String email) {
        // TODO Auto-generated method stub
        return jpaUserRepo.findByEmail(email);
    }

    @Override
    public UserEntity createUser(UserEntity userDetails) {
        // TODO Auto-generated method stub
        return jpaUserRepo.save(userDetails);
    }

    @Override
    public UserEntity updateUserInfo(String id) {
        return jpaUserRepo.findById(id)
                .map(entity -> {
                    // Update other fields as needed
                    entity.setIsRegistered(1);
                    entity.setInviteOn(utill.getShortDateFormayte());
                    entity.setUpdatedAt(LocalDateTime.now());
                    entity.setUpdatedBy(entity.getUuid());
                    return jpaUserRepo.save(entity);
                }).orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));
    }

}
