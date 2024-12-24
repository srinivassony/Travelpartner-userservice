package com.travelpartner.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travelpartner.user_service.entity.UserGalleryEntity;

@Repository
public interface UserGalleryRepo extends JpaRepository<UserGalleryEntity, String> {

}
