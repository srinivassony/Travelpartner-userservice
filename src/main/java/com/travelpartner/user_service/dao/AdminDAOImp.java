package com.travelpartner.user_service.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserServiceDTO> getUserRole(String role) {
        List<UserEntity> userEntity = jpaUserRepo.findByRole(role);

        return utillDTO.convertToUsersDTOList(userEntity);
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
}
