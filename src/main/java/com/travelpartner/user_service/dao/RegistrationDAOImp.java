package com.travelpartner.user_service.dao;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.repository.UserRepository;
import com.travelpartner.user_service.utill.Utills;

import jakarta.validation.Valid;

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
	public Optional<UserEntity> getUserById(String id) {
		return jpaUserRepo.findById(id);
	}

	@Override
	public UserEntity updateUserInfo(UserEntity userEntity, String id) {
		return jpaUserRepo.findById(id)
        .map(entity -> {
			// Update other fields as needed
			entity.setIsRegistered(1); 
			entity.setInviteOn(utill.getShortDateFormayte());
			entity.setUpdatedAt(LocalDateTime.now());
			entity.setUpdatedBy(userEntity.getUuid());     
            return jpaUserRepo.save(entity);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
	}

	

}
