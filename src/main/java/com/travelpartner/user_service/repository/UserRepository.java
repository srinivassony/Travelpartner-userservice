package com.travelpartner.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

	Optional<UserEntity> findByEmail(String email);
}
