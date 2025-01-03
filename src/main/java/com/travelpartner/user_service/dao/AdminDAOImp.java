package com.travelpartner.user_service.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.repository.UserRepository;
import com.travelpartner.user_service.utill.UtillDTO;

@Service
public class AdminDAOImp implements AdminDAO {

    @Autowired
    UserRepository jpaUserRepo;

    @Autowired
    UtillDTO utillDTO;

    @Override
    public Page<UserEntity> getUserRole(String role, Pageable pageable) {
        Page<UserEntity> user = jpaUserRepo.findByRole(role, pageable);
        if (!user.isEmpty()) {
            return user;
        } else {
            return Page.empty();
        }
    }

    @Override
    public UserServiceDTO updateUserInfo(String id, UserServiceDTO userServiceDTO, UserInfoDTO userDetails) {
        // TODO Auto-generated method stub

        return jpaUserRepo.findById(id)
                .map(entity -> {
                    // Update other fields as needed
                    if (userServiceDTO.getUserName() != null) {
                        entity.setPhone(userServiceDTO.getUserName());
                    }

                    if (userServiceDTO.getEmail() != null) {
                        entity.setPhone(userServiceDTO.getEmail());
                    }

                    if (userServiceDTO.getPhone() != null) {
                        entity.setPhone(userServiceDTO.getPhone());
                    }

                    if (userServiceDTO.getCountry() != null) {
                        entity.setCountry(userServiceDTO.getCountry());
                    }

                    if (userServiceDTO.getState() != null) {
                        entity.setState(userServiceDTO.getState());
                    }

                    if (userServiceDTO.getDob() != null) {
                        entity.setDob(userServiceDTO.getDob());
                    }

                    if (userServiceDTO.getGender() != null) {
                        entity.setGender(userServiceDTO.getGender());
                    }

                    entity.setUpdatedAt(LocalDateTime.now());
                    entity.setUpdatedBy(userDetails.getUuid());

                    UserEntity userEntity = jpaUserRepo.save(entity);
                    return utillDTO.convertToUserDTO(userEntity);

                }).orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));
    }

    @Override
    public List<UserServiceDTO> getUserList() {
        List<UserEntity> userEntity = jpaUserRepo.findAll();

        return utillDTO.convertToUsersDTOList(userEntity);
    }

    @Override
    public Optional<UserEntity> deleteUserInfo(String id) {
        Optional<UserEntity> user = jpaUserRepo.findById(id);
        if (user.isPresent()) {
            // Delete the user entity
            jpaUserRepo.deleteById(id);
            return user;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> getUserInfoById(String id) {
        // TODO Auto-generated method stub

        return jpaUserRepo.findById(id);
    }

    @Override
    public List<UserEntity> uploadUserInfo(List<UserEntity> users) {

        if (users != null && !users.isEmpty()) {
            // Save the users to the database
            List<UserEntity> savedUsers = jpaUserRepo.saveAll(users);
            return savedUsers; // Return the saved users list
        } else {
            // If the list is empty or null, you can handle accordingly (e.g., return an
            // empty list)
            return new ArrayList<>();
        }
    }

    @Override
    public List<UserEntity> getExisitingUsers(List<String> emails) {

        List<UserEntity> users = jpaUserRepo.findUsersByEmails(emails);

        return users;
    }
}
