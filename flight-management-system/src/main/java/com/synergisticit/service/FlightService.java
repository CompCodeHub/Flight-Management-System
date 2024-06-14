package com.synergisticit.service;

import java.util.List;

import com.synergisticit.model.Flight;
import com.synergisticit.model.FlightStatus;

public interface FlightService {
	
	Flight createFlight(Flight flight);
	
	List<Flight> findAllFlights();

	Flight findFlightByFlightCode(String flightCode);

	Flight updateFlightStatus(String flightId, FlightStatus flightStatus);
	
	void cancelFlight(String flightId);
}
