package com.travelpartner.user_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.entity.UserPostEntity;

@Repository
public interface UserPostRepo extends JpaRepository<UserPostEntity, String> {

}
