package com.travelpartner.user_service.utill;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.travelpartner.user_service.dao.RegistrationDAO;
import com.travelpartner.user_service.dto.UserInfoDTO;
import com.travelpartner.user_service.entity.UserEntity;

@Component
public class UserInfo implements UserDetailsService {

	@Autowired
	RegistrationDAO registrationDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserEntity> existingUser = registrationDAO.isUserExists(username);

		System.out.println("existingUser" + " " + existingUser.toString());

		if (existingUser.isEmpty()) {
			throw new UsernameNotFoundException("User not found: " + username);
		}

		return new UserInfoDTO(existingUser.get());
	}

}
