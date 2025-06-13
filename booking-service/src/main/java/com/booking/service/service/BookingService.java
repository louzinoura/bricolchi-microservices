package com.booking.service.service;

import com.booking.service.dto.BookingRequestDto;
import com.booking.service.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {

    BookingResponseDto createBooking(BookingRequestDto bookingRequest);

    List<BookingResponseDto> getBookingsForClient(Long clientId);

    List<BookingResponseDto> getBookingsForProvider(Long providerId);

    BookingResponseDto acceptBooking(Long bookingId);

    BookingResponseDto rejectBooking(Long bookingId);

    BookingResponseDto getBookingById(Long bookingId);

    List<BookingResponseDto> getAllBookings();
}