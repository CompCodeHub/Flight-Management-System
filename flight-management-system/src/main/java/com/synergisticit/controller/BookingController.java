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

import com.synergisticit.exception.DuplicateRecordException;
import com.synergisticit.exception.RecordNotFoundException;
import com.synergisticit.model.Booking;
import com.synergisticit.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/")
	public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
		try {
			Booking createdBooking = bookingService.createBooking(booking);
			return new ResponseEntity<Booking>(createdBooking, HttpStatus.CREATED);
		} catch (DuplicateRecordException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/booking/{bookingNumber}")
	public ResponseEntity<?> getBookingByBookingNumber(@PathVariable String bookingNumber) {
		try {
			Booking foundBooking = bookingService.findBookingByBookingNumber(bookingNumber);
			return new ResponseEntity<Booking>(foundBooking, HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<?> getBookingsByUserName(@PathVariable String username) {
		try {
			List<Booking> foundBookings = bookingService.findBookingsByUsername(username);
			return new ResponseEntity<List<Booking>>(foundBookings, HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{bookingNumber}")
	public ResponseEntity<?> updateSeatNumber(@PathVariable String bookingNumber, @RequestParam String seatNumber) {
		try {
			Booking updatedBooking = bookingService.updateBookingSeatNumber(bookingNumber, seatNumber);
			return new ResponseEntity<Booking>(updatedBooking, HttpStatus.OK);
		}catch (DuplicateRecordException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{bookingNumber}")
	public ResponseEntity<?> cancelBooking(@PathVariable String bookingNumber) {
		try {
			bookingService.cancelBooking(bookingNumber);
			return new ResponseEntity<String>("booking was cancelled successfully", HttpStatus.OK);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
