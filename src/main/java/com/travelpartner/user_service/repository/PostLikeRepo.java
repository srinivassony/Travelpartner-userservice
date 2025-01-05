package com.travelpartner.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.entity.PostLikeEntity;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLikeEntity, String> {

    Optional<PostLikeEntity> findByUserIdAndPostLike_Id(String userId, String postId);

}
