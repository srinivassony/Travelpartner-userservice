package com.travelpartner.user_service.dao;

import java.util.List;

import com.travelpartner.user_service.entity.UserEntity;

public interface AdminDAO {

    List<UserEntity> getUserRole(String role);

}
