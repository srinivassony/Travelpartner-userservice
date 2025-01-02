package com.travelpartner.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.entity.UserProfilePicEntity;

@Repository
public interface UserProfilePicRepo extends JpaRepository<UserProfilePicEntity, String>{

    Optional<UserProfilePicEntity> findByUserId(@Param("userId") String userId);
}
