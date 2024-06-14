package com.synergisticit.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.synergisticit.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
}
