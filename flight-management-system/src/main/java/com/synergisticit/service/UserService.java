package com.synergisticit.service;

import java.util.List;

import com.synergisticit.model.Booking;
import com.synergisticit.model.User;

public interface UserService {

	User createUser(User user);

	List<User> getAllUsers();

	User findUserByUsername(String username);

	User findUserByEmail(String email);

	User updateUserPhone(String userId, String phoneNumber);

	void deleteUser(String userId);

}