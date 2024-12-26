package com.travelpartner.user_service.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.travelpartner.user_service.utill.JwtTokenProvider;
import com.travelpartner.user_service.utill.UserInfo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	UserInfo userInfo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = null;
		String email = null;
		String header = request.getHeader("Authorization");

		if (null != header && header.startsWith("Bearer ")) {
			token = header.substring(7);
			email = jwtTokenProvider.getEmailFromToken(token);
		}
		
		System.out.println("email"+" "+email);

		if (null != email && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userInfo.loadUserByUsername(email);
			if (jwtTokenProvider.validateToken(token)) {
				UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				userToken.setDetails(new WebAuthenticationDetails(request));
				request.setAttribute("user", userDetails);
				SecurityContextHolder.getContext().setAuthentication(userToken);
			}
		}
		
		filterChain.doFilter(request, response);

	}

}
