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
import com.synergisticit.model.Aircraft;
import com.synergisticit.service.AircraftService;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

	@Autowired
	private AircraftService aircraftService;

	@PostMapping("/")
	public ResponseEntity<Aircraft> createAircraft(@RequestBody Aircraft aircraft) {
		Aircraft createdAircraft = aircraftService.createAircraft(aircraft);
		return new ResponseEntity<Aircraft>(createdAircraft, HttpStatus.CREATED);
	}

	@GetMapping("/getByAirline/{airlineId}")
	public ResponseEntity<List<Aircraft>> getAircraftsByAirlineId(@PathVariable String airlineId) {
		List<Aircraft> aircrafts = aircraftService.findAircraftsByAirline(airlineId);
		return new ResponseEntity<List<Aircraft>>(aircrafts, HttpStatus.OK);
	}
	
	@GetMapping("/getByCapacity/{capacity}")
	public ResponseEntity<List<Aircraft>> getAircraftsByCapacity(@PathVariable Integer capacity){
		List<Aircraft> aircraftsByCapacity = aircraftService.findAircraftsByCapacity(capacity);
		return new ResponseEntity<List<Aircraft>>(aircraftsByCapacity, HttpStatus.OK);
	}
	
	@PutMapping("/{aircraftId}")
	public ResponseEntity<?> updateAircraftCapacity(@PathVariable String aircraftId, @RequestParam Integer capacity){
		try {
			Aircraft updatedAirCraft= aircraftService.updateAirCraftCapacity(aircraftId, capacity);
			return new ResponseEntity<Aircraft>(updatedAirCraft, HttpStatus.OK);
		}catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{aircraftId}")
	public ResponseEntity<String> deleteAircraft(@PathVariable String aircraftId) {
		aircraftService.deleteAircraft(aircraftId);
		return new ResponseEntity<String>("aircraft with id=" + aircraftId + " was deleted!", HttpStatus.OK);
	}
	
	

}
