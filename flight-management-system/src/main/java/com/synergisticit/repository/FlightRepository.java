package com.synergisticit.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.synergisticit.model.Flight;

public interface FlightRepository extends MongoRepository<Flight, String> {
	Optional<Flight> findByFlightCode(String flightCode);
}
