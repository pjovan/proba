package com.example.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PasswordChangeDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.PasswordChangeException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;

@Service
@Transactional
public class UserService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Attempts to change clients password
	 * 
	 * @param passwordChangeDto dto containing old and new password that user has
	 *                          sent
	 * @param token             token containing user's email
	 */
	public void changePassword(PasswordChangeDTO passwordChangeDto, String token) {
		String username = jwtUtil.extractUsername(token);
		Optional<UserEntity> existingUser = userRepository.findByUsername(username);
		if (existingUser.isEmpty()) {
			throw new ResourceNotFoundException("User not found.");
		}

		UserEntity user = existingUser.get();
		if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword())) {
			throw new PasswordChangeException("The old password you have entered is incorrect.");
		}
		if (passwordEncoder.matches(passwordChangeDto.getNewPassword(), user.getPassword())) {
			throw new PasswordChangeException("Your new password must be different from your previous password.");
		}
		user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
		userRepository.save(user);

	}
}
