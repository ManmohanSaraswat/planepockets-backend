package com.planepockets.proton.service;

import com.planepockets.proton.model.JwtRequest;
import com.planepockets.proton.model.User;
import com.planepockets.proton.repository.UserRepository;
import com.planepockets.proton.util.JwtUtil;
import com.planepockets.proton.wrapperclasses.UserPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

	static final Logger log = LoggerFactory.getLogger(JwtService.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	public UserPojo createJwtToken(JwtRequest jwtRequest) throws BadCredentialsException, UsernameNotFoundException {
		String userName = jwtRequest.getLoginId();
		String userPassword = jwtRequest.getPassword();
		final UserDetails userDetails = loadUserByUsername(userName);
		authenticate(userName, userPassword);

		String token = jwtUtil.generateToken(userDetails);
		User user = userRepository.findById(userName).get();
		UserPojo pojo = new UserPojo(user.getLoginId(), user.getFullName(), user.getContactNumber(), token);
		userAuthenticationService.saveToken(pojo.getLoginId(), "Bearer " + pojo.getJwtToken());
		log.info("Token created successfully for the request {}", jwtRequest);
		return pojo;
	}

	private void authenticate(String userName, String password) throws BadCredentialsException, DisabledException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		} catch (DisabledException e) {
			log.warn("User is disabled userName {}", userName);
			throw new DisabledException("User is disabled");
		} catch (BadCredentialsException e) {
			log.warn("Credentials are invalid for userName {}", userName);
			throw new BadCredentialsException("Invalid Credentials");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findById(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(),
					getAuthorities(user));
		} else {
			log.warn("UserName is invalid {}", username);
			throw new UsernameNotFoundException("Username is invalid");
		}
	}

	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;
	}

}
