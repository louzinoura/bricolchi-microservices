package com.booking.service.controller;

import com.booking.service.dto.BookingRequestDto;
import com.booking.service.dto.BookingResponseDto;
import com.booking.service.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // POST /api/bookings - Postuler à une annonce
    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingRequestDto bookingRequest) {
        BookingResponseDto createdBooking = bookingService.createBooking(bookingRequest);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    // GET /api/bookings/client/{id} - Candidatures reçues (client)
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsForClient(@PathVariable Long clientId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsForClient(clientId);
        return ResponseEntity.ok(bookings);
    }

    // GET /api/bookings/provider/{id} - Candidatures envoyées (prestataire)
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsForProvider(@PathVariable Long providerId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsForProvider(providerId);
        return ResponseEntity.ok(bookings);
    }

    // PUT /api/bookings/{id}/accept - Accepter une candidature
    @PutMapping("/{bookingId}/accept")
    public ResponseEntity<BookingResponseDto> acceptBooking(@PathVariable Long bookingId) {
        BookingResponseDto acceptedBooking = bookingService.acceptBooking(bookingId);
        return ResponseEntity.ok(acceptedBooking);
    }

    // PUT /api/bookings/{id}/reject - Refuser une candidature
    @PutMapping("/{bookingId}/reject")
    public ResponseEntity<BookingResponseDto> rejectBooking(@PathVariable Long bookingId) {
        BookingResponseDto rejectedBooking = bookingService.rejectBooking(bookingId);
        return ResponseEntity.ok(rejectedBooking);
    }

    // GET /api/bookings/{id} - Détails d'une réservation
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long bookingId) {
        BookingResponseDto booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    // GET /api/bookings - Liste de toutes les réservations (pour admin)
    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        List<BookingResponseDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}