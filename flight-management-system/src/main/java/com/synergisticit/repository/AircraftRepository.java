package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.synergisticit.model.Aircraft;

public interface AircraftRepository extends MongoRepository<Aircraft, String> {

	List<Aircraft> findAircraftsByCapacity(Integer capacity);
	
	List<Aircraft> findAircraftsByAirlineId(String airlineId);
}
