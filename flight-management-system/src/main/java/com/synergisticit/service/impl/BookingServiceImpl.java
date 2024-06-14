package com.synergisticit.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.exception.DuplicateRecordException;
import com.synergisticit.exception.RecordNotFoundException;
import com.synergisticit.model.Booking;
import com.synergisticit.model.BookingStatus;
import com.synergisticit.model.User;
import com.synergisticit.repository.BookingRepository;
import com.synergisticit.service.BookingService;
import com.synergisticit.service.UserService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserService userService;

	private String generateBookingNumber() {
		return UUID.randomUUID().toString();
	}

	@Override
	public Booking createBooking(Booking booking) {

		// Check for duplicate seatNumber in same flight
		if (bookingRepository.existsByFlightIdAndSeatNumber(booking.getFlightId(), booking.getSeatNumber())) {
			throw new DuplicateRecordException("Booking with given seat number for the flight already exists!");
		}

		// Set unique generated booking number
		String bookingNumber = generateBookingNumber();
		booking.setBookingNumber(bookingNumber);

		return bookingRepository.save(booking);
	}

	@Override
	public Booking findBookingByBookingNumber(String bookingNumber) {
		return bookingRepository.findByBookingNumber(bookingNumber).orElseThrow(
				() -> new RecordNotFoundException("Booking with bookingNumber=" + bookingNumber + " was not found!"));
	}

	@Override
	public List<Booking> findBookingsByUsername(String username) {

		// find user's id
		User user = userService.findUserByUsername(username);

		// find boooking by user ids
		return bookingRepository.findByUserId(user.getId());
	}

	@Override
	public List<Booking> findBookingsByUserId(String userId) {
		return bookingRepository.findByUserId(userId);
	}

	@Override
	public Booking updateBookingSeatNumber(String bookingNumber, String seatNumber) {
		// Find booking
		Booking booking = bookingRepository.findByBookingNumber(bookingNumber).orElseThrow(() -> new RecordNotFoundException("Booking with booking number=" + bookingNumber + " was not found!"));
		
		// Check for duplicate seatNumber in same flight
		if (bookingRepository.existsByFlightIdAndSeatNumber(booking.getFlightId(), seatNumber)) {
			throw new DuplicateRecordException("Booking with given seat number for the flight already exists!");
		}
		
		// set seat number
		booking.setSeatNumber(seatNumber);
		
		return bookingRepository.save(booking);
	}

	@Override
	public void cancelBooking(String bookingNumber) {

		// Find booking by booking number
		Optional<Booking> bookingOptional = bookingRepository.findByBookingNumber(bookingNumber);

		// If booking not found, throw exception
		if (bookingOptional.isEmpty()) {
			throw new RecordNotFoundException("Booking with bookingNumber=" + bookingNumber + " was not found!");
		}

		// Get the booking object
		Booking booking = bookingOptional.get();

		// Set booking status to cancelled
		booking.setBookingStatus(BookingStatus.CANCELLED);

		// Save updated booking
		bookingRepository.save(booking);
	}
}
