package com.synergisticit.service;

import java.util.List;

import com.synergisticit.model.Booking;

public interface BookingService {
	
	Booking createBooking(Booking booking);
	
	Booking findBookingByBookingNumber(String bookingNumber);
	
	List<Booking> findBookingsByUsername(String username);
	
	List<Booking> findBookingsByUserId(String userId);

	Booking updateBookingSeatNumber(String bookingNumber, String seatNumber);

	void cancelBooking(String bookingNumber);
}
