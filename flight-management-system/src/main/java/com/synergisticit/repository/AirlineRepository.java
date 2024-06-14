package com.synergisticit.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.synergisticit.model.Airline;

public interface AirlineRepository extends MongoRepository<Airline, String> {
	boolean existsByCode(String code);
	Optional<Airline> findByCode(String code);
}
