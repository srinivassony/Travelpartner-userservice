package com.travelpartner.user_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.repository.UserRepository;

@Service
public class AdminDAOImp implements AdminDAO {

    @Autowired
    UserRepository jpaUserRepo;

    @Override
    public List<UserEntity> getUserRole(String role) {
        return jpaUserRepo.findByRole(role);
    }
}
