package com.synergisticit.service;

import java.util.List;

import com.synergisticit.model.Airline;

public interface AirlineService {
	
	Airline createAirline(Airline airline);
	
	List<Airline> findAllAirlines();

	Airline findAirlineByCode(String airlineCode);

	Airline updateAirlineName(String airlineId, String airlineName);

	void deleteAirline(String airlineId);
}
