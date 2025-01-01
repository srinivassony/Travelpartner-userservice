package com.travelpartner.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.entity.UserPostEntity;

@Repository
public interface UserPostRepo extends JpaRepository<UserPostEntity, String> {

}
