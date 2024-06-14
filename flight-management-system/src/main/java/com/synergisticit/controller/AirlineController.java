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
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.exception.DuplicateRecordException;
import com.synergisticit.exception.RecordNotFoundException;
import com.synergisticit.model.Airline;
import com.synergisticit.model.User;
import com.synergisticit.service.AirlineService;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

	@Autowired
	private AirlineService airlineService;

	@PostMapping("/")
	public ResponseEntity<?> createAirline(@RequestBody Airline airline) {

		try {
			Airline createdAirline = airlineService.createAirline(airline);
			return new ResponseEntity<Airline>(createdAirline, HttpStatus.CREATED);
		} catch (DuplicateRecordException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllAirlines() {
		List<Airline> airlines = airlineService.findAllAirlines();
		return new ResponseEntity<List<Airline>>(airlines, HttpStatus.OK);
	}

	@GetMapping("/{airlineCode}")
	public ResponseEntity<?> getAirlineByCode(@PathVariable String airlineCode) {
		try {
			Airline foundAirline = airlineService.findAirlineByCode(airlineCode);
			return new ResponseEntity<Airline>(foundAirline, HttpStatus.CREATED);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{airlineId}")
	public ResponseEntity<?> updateAirlineName(@PathVariable String airlineId, @RequestBody String airlineName) {
		try {
			Airline updatedAirline = airlineService.updateAirlineName(airlineId, airlineName);
			return new ResponseEntity<Airline>(updatedAirline, HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{airlineId}")
	public ResponseEntity<String> deleteAirline(@PathVariable String airlineId) {
		airlineService.deleteAirline(airlineId);
		return new ResponseEntity<String>("Airline with id=" + airlineId + " was deleted!", HttpStatus.OK);
	}
}
