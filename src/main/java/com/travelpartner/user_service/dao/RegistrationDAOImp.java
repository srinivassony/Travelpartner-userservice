package com.travelpartner.user_service.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class RegistrationDAOImp implements RegistrationDAO {
	
	@Autowired
	UserRepository jpaUserRepo;

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

	

}
