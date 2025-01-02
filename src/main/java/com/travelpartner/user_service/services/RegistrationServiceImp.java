package com.travelpartner.user_service.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.travelpartner.user_service.config.CustomResponse;
import com.travelpartner.user_service.dao.RegistrationDAO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.dto.UserServiceDTO;
import com.travelpartner.user_service.entity.UserEntity;
import com.travelpartner.user_service.kafka.UserEmailProducer;
import com.travelpartner.user_service.utill.HtmlTemplate;
import com.travelpartner.user_service.utill.JwtTokenProvider;
import com.travelpartner.user_service.utill.UserInfo;
import com.travelpartner.user_service.utill.Utills;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Service
public class RegistrationServiceImp implements RegistrationService {

    @Autowired
    RegistrationDAO registrationDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Utills utill;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // You can use JWT or sessions for handling authentication tokens

    @Autowired
    UserInfo userInfo;

    @Autowired
    UserEmailProducer userEmailProducer;

    @Autowired
    HtmlTemplate htmlTemplate;

    @Autowired
    Utills utills;

    @Override
    public ResponseEntity<?> createUserInfo(@Valid UserServiceDTO userServiceDTO, HttpServletRequest req,
            HttpServletResponse res) {
        try {
            Optional<UserEntity> existingUser = registrationDAO.isUserExists(userServiceDTO.getEmail());

            if (existingUser.isPresent()) {

                String errorMessages = "User email already exists. Try with a different email.";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                // If the user exists, return a message with a bad status
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            String userName = userServiceDTO.getUserName() != null ? userServiceDTO.getUserName() : null;

            String email = userServiceDTO.getEmail() != null ? userServiceDTO.getEmail() : null;

            String role;
            if (userServiceDTO.getIsAdmin() != null) {
                role = "ROLE_ADMIN,";
            } else {
                role = "ROLE_USER,";
            }

            String password = userServiceDTO.getPassword() != null
                    ? passwordEncoder.encode(userServiceDTO.getPassword())
                    : null;

            String uuid = utill.generateString(36);

            LocalDateTime createdAt = LocalDateTime.now();

            String createdBy = uuid;

            UserEntity userDetails = new UserEntity();

            userDetails.setUserName(userName);
            userDetails.setEmail(email);
            userDetails.setPassword(password);
            userDetails.setRole(role);
            userDetails.setUuid(uuid);
            userDetails.setCreatedAt(createdAt);
            userDetails.setCreatedBy(createdBy);

            UserEntity userInfo = registrationDAO.createUser(userDetails);

            if (userInfo.getId() == null) {

                String errorMessages = "User not created. Please try again!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                // If the user exists, return a message with a bad status
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            String subject = userInfo.getUserName() + " " + "you are invited to Travel-partner";
            String content = htmlTemplate.InviteUser(userInfo.getId(), userInfo.getUserName());

            // Create a map to store name and id
            Map<String, Object> userData = new HashMap<>();
            userData.put("subject", subject);
            userData.put("toEmailId", userInfo.getEmail());
            userData.put("content", content);

            userEmailProducer.sendMessage(userData);

            CustomResponse<UserEntity> responseBody = new CustomResponse<>(userInfo, "CREATED", HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (Exception e) {
            String stackTrace = utills.getStackTraceAsString(e);

            CustomResponse<String> responseBody = new CustomResponse<>(stackTrace, "BAD_REQUEST",
                    HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> athunticateUser(UserEntity userEntity, HttpServletRequest req, HttpServletResponse res) {
        try {
            if (userEntity.getEmail().isBlank() || userEntity.getEmail().isEmpty()) {
                String errorMessages = "Email is required!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                // If the user exists, return a message with a bad status
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            if (userEntity.getPassword().isBlank() || userEntity.getPassword().isEmpty()) {
                String errorMessages = "Password is required!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                // If the user exists, return a message with a bad status
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            UserDetails userDetails = userInfo.loadUserByUsername(userEntity.getEmail());

            System.out.println("userDetails" + " " + userDetails);

            // Load the user by email
            // Manually authenticate using the AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEntity.getEmail(), userEntity.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Optionally, generate a JWT token for the user (or manage sessions)
            String token = jwtTokenProvider.generateToken(authentication);

            System.out.println("token" + " " + token);

            System.out.println("authentication" + " " + authentication);

            // Prepare user details for the response
            UserInfoDTO user = (UserInfoDTO) authentication.getPrincipal();
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", user.getId()); // Assuming you have a getId() method
            response.put("username", user.getUserName());
            response.put("email", user.getEmail());
            response.put("roles",
                    user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

            CustomResponse<?> responseBody = new CustomResponse<>(response, "SUCCESS", HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (UsernameNotFoundException ex) {
            // Handle case when user is not found
            CustomResponse<String> responseBody = new CustomResponse<>("User details not found!", "BAD_CREDENTIALS",
                    HttpStatus.UNAUTHORIZED.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException ex) {
            // Handle case when credentials are invalid
            CustomResponse<String> responseBody = new CustomResponse<>("Incorrect email or password", "BAD_CREDENTIALS",
                    HttpStatus.UNAUTHORIZED.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            // Handle any other exceptions
            CustomResponse<String> responseBody = new CustomResponse<>(ex.getMessage(), "ERROR",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> onBoardinguserInfo(HttpServletRequest req, HttpServletResponse res, String id) {
        try {
            System.out.println("id" + " " + id);

            if (id.isBlank()) {

                String errorMessages = "Id is required!";

                CustomResponse<String> responseBody = new CustomResponse<>(errorMessages, "BAD_REQUEST",
                        HttpStatus.BAD_REQUEST.value(), req.getRequestURI(), LocalDateTime.now());

                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

            UserEntity updateUser = registrationDAO.updateUserInfo(id);

            CustomResponse<?> responseBody = new CustomResponse<>(updateUser, "UPDATED", HttpStatus.OK.value(),
                    req.getRequestURI(), LocalDateTime.now());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception ex) {
            // Handle any other exceptions
            CustomResponse<String> responseBody = new CustomResponse<>(ex.getMessage(), "ERROR",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), req.getRequestURI(), LocalDateTime.now());
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
