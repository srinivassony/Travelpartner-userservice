package com.travelpartner.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.entity.PostCommentEntity;

@Repository
public interface PostCommentRepo extends JpaRepository<PostCommentEntity, String>{

}
