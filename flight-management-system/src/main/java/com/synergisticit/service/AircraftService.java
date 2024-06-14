package com.synergisticit.service;

import java.util.List;

import com.synergisticit.model.Aircraft;

public interface AircraftService {
	
	Aircraft createAircraft(Aircraft aircraft);

	List<Aircraft> findAircraftsByCapacity(Integer capacity);
	
	List<Aircraft> findAircraftsByAirline(String airlineId);
	
	Aircraft updateAirCraftCapacity(String aircraftId, Integer capacity);

	void deleteAircraft(String aircraftId);
}