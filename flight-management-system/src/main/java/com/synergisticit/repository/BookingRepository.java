package com.synergisticit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.synergisticit.model.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {
	boolean existsByFlightIdAndSeatNumber(String flightId, String seatNumber);
	Optional<Booking> findByBookingNumber(String bookingNumber);
	List<Booking> findByUserId(String userId);
}
