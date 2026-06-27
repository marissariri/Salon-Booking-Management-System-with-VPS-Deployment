package com.salon.booking.controller;

import com.salon.booking.dto.BookingRequestDTO;
import com.salon.booking.dto.BookingResponseDTO;
import com.salon.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO request, Principal principal) {
        String userEmail = principal.getName();
        BookingResponseDTO response = bookingService.createBooking(request, userEmail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
