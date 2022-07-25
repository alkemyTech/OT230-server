package com.alkemy.ong.auth.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.response.AuthenticationResponse;
import com.alkemy.ong.repository.UserRepository;
import com.amazonaws.services.pinpoint.model.ForbiddenException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtils jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> userDB = userRepository.findByEmail(email);
		if (!userDB.isPresent()) {
			throw new UsernameNotFoundException("User not found");
		}
	
        return new User(userDB.get().getEmail(), userDB.get().getPassword(), mapRoles(userDB.get().getRoleId()));
	}

	private Collection<? extends GrantedAuthority> mapRoles(Set<RoleEntity> roles) {
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
	}

	public AuthenticationResponse authentication(String email, String password) {
		UserDetails userDetails;
		try {
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			SecurityContextHolder.getContext().setAuthentication(auth);
			userDetails = (UserDetails) auth.getPrincipal();
		} catch (BadCredentialsException e) {
			throw new ForbiddenException("Incorrect username or password");
		}

		String jwt = jwtUtil.generateToken(userDetails);

		return new AuthenticationResponse(jwt);
	}
}
