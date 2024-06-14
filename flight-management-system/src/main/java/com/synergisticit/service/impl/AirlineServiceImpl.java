package com.synergisticit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.exception.DuplicateRecordException;
import com.synergisticit.exception.RecordNotFoundException;
import com.synergisticit.model.Airline;
import com.synergisticit.repository.AirlineRepository;
import com.synergisticit.service.AirlineService;

@Service
public class AirlineServiceImpl implements AirlineService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Override
	public Airline createAirline(Airline airline) {

		// Check duplicate airline code
		if (airlineRepository.existsByCode(airline.getCode())) {
			throw new DuplicateRecordException("Airline with code=" + airline.getCode() + " already exists!");
		}

		return airlineRepository.save(airline);
	}

	@Override
	public Airline findAirlineByCode(String airlineCode) {
		return airlineRepository.findByCode(airlineCode)
				.orElseThrow(() -> new RecordNotFoundException("Airline with code=" + airlineCode + " was not found!"));
	}

	@Override
	public Airline updateAirlineName(String airlineId, String airlineName) {
		// Retrive airline by airlineId
		Airline airline = airlineRepository.findById(airlineId).orElseThrow(() -> new RecordNotFoundException("Airline with ID=" + airlineId + " was not found!"));
		
		// Update Airline name
		airline.setName(airlineName);
		
		return airlineRepository.save(airline);
	}

	@Override
	public void deleteAirline(String airlineId) {
		airlineRepository.deleteById(airlineId);
	}

	@Override
	public List<Airline> findAllAirlines() {
		return airlineRepository.findAll();
	}

}
