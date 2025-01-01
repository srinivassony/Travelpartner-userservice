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

    // @Override
    // public List<UserEntity> uploadUserData(String name, String email, String
    // phone, String role, String password,
    // String country, String uuid) {
    // // TODO Auto-generated method stub

    // UserEntity user = new UserEntity();
    // user.setUserName(name);
    // user.setEmail(email);
    // user.setPhone(phone);
    // user.setRole(role);
    // user.setPassword(password);
    // user.setCountry(country);
    // user.setUuid(uuid);
    // jpaUserRepo.save(user);

    // return (List<UserEntity>) jpaUserRepo.save(user);

    // }

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
}
// @Override
// public UserEntity uploadUserInfo(Sheet sheet, UserEntity userEntity) {
// // TODO Auto-generated method stub

// List<UserEntity> usersToSave = new ArrayList<>();
// Iterator<Row> rowIterator = sheet.iterator();

// // Skip the header row (if it exists)
// if (rowIterator.hasNext())
// rowIterator.next();

// while (rowIterator.hasNext()) {
// Row row = rowIterator.next();

// // Get the data from each cell in the row (assuming 4 columns: UserName,
// Email,
// // Password, Role)
// String userName = row.getCell(0).getStringCellValue();
// String email = row.getCell(1).getStringCellValue();
// String password = row.getCell(2).getStringCellValue();
// String role = row.getCell(3).getStringCellValue();

// // Create a new UserEntity and populate it with the data
// // UserEntity user = new UserEntity();
// userEntity.setUserName(userName);
// userEntity.setUserName(userName);
// userEntity.setEmail(email);
// userEntity.setPassword(password); // Consider hashing the password before
// saving
// userEntity.setRole(role);
// userEntity.setCreatedAt(LocalDateTime.now());
// userEntity.setCreatedBy("Admin");

// userEntity.setCreatedAt(LocalDateTime.now()); // Set current timestamp as
// creation time
// userEntity.setCreatedBy("Admin"); // Assume "Admin" as the user who created
// the record
// userEntity.setUpdatedAt(LocalDateTime.now()); // Set current timestamp as
// updated time
// userEntity.setUpdatedBy("Admin"); // Assume "Admin" as the user who updated
// the record
// userEntity.setInviteOn(null); // Set current time for invite date
// userEntity.setIsRegistered(1); // Default registration status
// userEntity.setIsInvited(0); // Assume the user is invited
// userEntity.setLogin(0); // Assume user hasn't logged in yet
// userEntity.setLogout(0); // Assume user hasn't logged out yet
// userEntity.setPhone("Not Provided"); // Default value for phone
// userEntity.setState("Not Provided"); // Default value for state
// userEntity.setCountry("Not Provided"); // Default value for country
// userEntity.setDob(null); // Default date of birth (can be changed as needed)
// userEntity.setDob(role);
// userEntity.setGender("Not Provided"); // Default gender value
// userEntity.setUuid(UUID.randomUUID().toString());

// // Save the user entity into the database
// usersToSave.add(userEntity);
// }
// return (UserEntity) jpaUserRepo.saveAll(usersToSave);
// }
