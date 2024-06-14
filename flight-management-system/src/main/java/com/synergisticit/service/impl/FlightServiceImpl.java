package com.synergisticit.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.exception.RecordNotFoundException;
import com.synergisticit.model.Airline;
import com.synergisticit.model.Flight;
import com.synergisticit.model.FlightStatus;
import com.synergisticit.repository.AirlineRepository;
import com.synergisticit.repository.FlightRepository;
import com.synergisticit.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private AirlineRepository airlineRepository;;

	private String generateFlightCode(String airlineCode) {

		// Generate unique 6 character code
		String uniqueCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

		// Get flight code
		String flightCode = airlineCode + uniqueCode;

		// Ensure uniqueness
		Optional<Flight> existingFlight = flightRepository.findByFlightCode(flightCode);
		while (existingFlight.isPresent()) {
			uniqueCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
			flightCode = airlineCode + uniqueCode;
			existingFlight = flightRepository.findByFlightCode(flightCode);
		}

		return flightCode;
	}

	@Override
	public Flight createFlight(Flight flight) {

		// Find associated airline
		Airline airline = airlineRepository.findById(flight.getAirlineId())
				.orElseThrow(() -> new RecordNotFoundException("Airline with id=" + " was not found!"));

		// Generate flight code
		String flightCode = generateFlightCode(airline.getCode());

		// Set flight code
		flight.setFlightCode(flightCode);

		return flightRepository.save(flight);
	}

	@Override
	public Flight findFlightByFlightCode(String flightCode) {
		return flightRepository.findByFlightCode(flightCode)
				.orElseThrow(() -> new RecordNotFoundException("Flight with code=" + flightCode + " was not found!"));
	}

	@Override
	public void cancelFlight(String id) {

		// Find flight
		Optional<Flight> flightOptional = flightRepository.findById(id);

		// if flight not found, throw exception
		if (flightOptional.isEmpty()) {
			throw new RecordNotFoundException("Flight with id=" + id + " was not found!");
		}

		// Get flight object
		Flight flight = flightOptional.get();

		// Set booking status to cancelled
		flight.setStatus(FlightStatus.CANCELED);

		// save updated flight
		flightRepository.save(flight);
	}

	@Override
	public Flight updateFlightStatus(String flightId, FlightStatus flightStatus) {
		
		// find flight
		Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new RecordNotFoundException("Flight with id=" + flightId + " was not found!"));
		
		// set flight status
		flight.setStatus(flightStatus);
		
		return flightRepository.save(flight);
	}

	@Override
	public List<Flight> findAllFlights() {
		return flightRepository.findAll();
	}

}
