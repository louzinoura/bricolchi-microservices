package com.booking.service.service;

import com.booking.service.dto.BookingRequestDto;
import com.booking.service.dto.BookingResponseDto;
import com.booking.service.entity.Booking;
import com.booking.service.enums.BookingStatus;
import com.booking.service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public BookingResponseDto createBooking(BookingRequestDto bookingRequest) {
        Booking booking = new Booking(
                bookingRequest.getListingId(),
                bookingRequest.getClientId(),
                bookingRequest.getProviderId(),
                bookingRequest.getMessage(),
                bookingRequest.getProposedPrice(),
                bookingRequest.getServiceDate()
        );

        Booking savedBooking = bookingRepository.save(booking);
        return convertToResponseDto(savedBooking);
    }

    @Override
    public List<BookingResponseDto> getBookingsForClient(Long clientId) {
        List<Booking> bookings = bookingRepository.findByClientIdOrderByCreatedAtDesc(clientId);
        return bookings.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getBookingsForProvider(Long providerId) {
        List<Booking> bookings = bookingRepository.findByProviderIdOrderByCreatedAtDesc(providerId);
        return bookings.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto acceptBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        booking.setStatus(BookingStatus.ACCEPTED);
        Booking updatedBooking = bookingRepository.save(booking);

        return convertToResponseDto(updatedBooking);
    }

    @Override
    public BookingResponseDto rejectBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        booking.setStatus(BookingStatus.REJECTED);
        Booking updatedBooking = bookingRepository.save(booking);

        return convertToResponseDto(updatedBooking);
    }

    @Override
    public BookingResponseDto getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        return convertToResponseDto(booking);
    }

    @Override
    public List<BookingResponseDto> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    // Méthode utilitaire pour convertir Entity vers DTO
    private BookingResponseDto convertToResponseDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setListingId(booking.getListingId());
        dto.setClientId(booking.getClientId());
        dto.setProviderId(booking.getProviderId());
        dto.setStatus(booking.getStatus());
        dto.setMessage(booking.getMessage());
        dto.setProposedPrice(booking.getProposedPrice());
        dto.setServiceDate(booking.getServiceDate());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        return dto;
    }
}