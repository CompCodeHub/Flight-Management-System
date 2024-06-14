package com.synergisticit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.exception.DuplicateRecordException;
import com.synergisticit.exception.RecordNotFoundException;
import com.synergisticit.model.Booking;
import com.synergisticit.model.User;
import com.synergisticit.repository.UserRepository;
import com.synergisticit.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User user) {

		// Check duplicate email
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new DuplicateRecordException("account with specified email already exists!");
		}

		// Check duplicate username
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new DuplicateRecordException("Username already taken!");
		}

		// Check duplicate phonenumber
		if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
			throw new DuplicateRecordException("phone number already in use with other account!");
		}

		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RecordNotFoundException("User with username=" + username + " was not found!"));
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("User with email=" + email + " was not found!"));
	}

	@Override
	public User updateUserPhone(String userId, String phoneNumber) {
		// Retrieve user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User with ID=" + userId + " was not found!"));

        // Check if the new phone number is already in use by another user
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateRecordException("phone number already in use with other account!");
        }

        // Update the phone number
        user.setPhoneNumber(phoneNumber);

        // Save the updated user back to the repository
        return userRepository.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

}
