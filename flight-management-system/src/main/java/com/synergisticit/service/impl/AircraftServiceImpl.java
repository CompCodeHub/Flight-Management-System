package com.synergisticit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.exception.RecordNotFoundException;
import com.synergisticit.model.Aircraft;
import com.synergisticit.repository.AircraftRepository;
import com.synergisticit.service.AircraftService;

@Service
public class AircraftServiceImpl implements AircraftService {

	@Autowired
	private AircraftRepository aircraftRepository;

	@Override
	public Aircraft createAircraft(Aircraft aircraft) {
		return aircraftRepository.save(aircraft);
	}

	@Override
	public List<Aircraft> findAircraftsByCapacity(Integer capacity) {
		return aircraftRepository.findAircraftsByCapacity(capacity);
	}

	@Override
	public List<Aircraft> findAircraftsByAirline(String airlineId) {
		return aircraftRepository.findAircraftsByAirlineId(airlineId);
	}

	@Override
	public void deleteAircraft(String aircraftId) {
		aircraftRepository.deleteById(aircraftId);
	}

	@Override
	public Aircraft updateAirCraftCapacity(String aircraftId, Integer capacity) {

		// Find airline
		Aircraft aircraft = aircraftRepository.findById(aircraftId)
				.orElseThrow(() -> new RecordNotFoundException("Aircraft with id=" + aircraftId + " was not found!"));

		// update capacity
		aircraft.setCapacity(capacity);
		
		return aircraftRepository.save(aircraft);
	}

}
