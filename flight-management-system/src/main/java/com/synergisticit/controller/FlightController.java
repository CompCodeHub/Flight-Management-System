package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.exception.RecordNotFoundException;
import com.synergisticit.model.Flight;
import com.synergisticit.model.FlightStatus;
import com.synergisticit.service.FlightService;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

	@Autowired
	private FlightService flightService;

	@PostMapping("/")
	public ResponseEntity<?> createFlight(@RequestBody Flight flight) {
		try {
			Flight createdFlight = flightService.createFlight(flight);
			return new ResponseEntity<Flight>(createdFlight, HttpStatus.CREATED);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Flight>> getAllFlights() {
		List<Flight> allFlights = flightService.findAllFlights();
		return new ResponseEntity<List<Flight>>(allFlights, HttpStatus.OK);
	}

	@GetMapping("/{flightCode}")
	public ResponseEntity<?> getFlightByFlightCode(@PathVariable String flightCode) {
		try {
			Flight foundFlight = flightService.findFlightByFlightCode(flightCode);
			return new ResponseEntity<Flight>(foundFlight, HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{flightId}")
	public ResponseEntity<?> updateFlightStatus(@PathVariable String flightId, @RequestParam FlightStatus status) {
		try {
			Flight updatedFlight = flightService.updateFlightStatus(flightId, status);
			return new ResponseEntity<Flight>(updatedFlight, HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{flightId}")
	public ResponseEntity<?> cancelFlight(@PathVariable String flightId) {
		try {
			flightService.cancelFlight(flightId);
			return new ResponseEntity<String>("Flight with id=" + flightId + " was cancelled!", HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
