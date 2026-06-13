package com.salon.booking.controller;

import com.salon.booking.dto.BookingDashboardDTO;
import com.salon.booking.dto.PaymentDashboardDTO;
import com.salon.booking.entity.Booking;
import com.salon.booking.entity.Payment;
import com.salon.booking.repository.BookingRepository;
import com.salon.booking.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    @GetMapping("/bookings")
    public List<BookingDashboardDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(booking -> {
            BookingDashboardDTO dto = new BookingDashboardDTO();
            dto.setId(booking.getId());
            dto.setUserName(booking.getUser() != null ? booking.getUser().getFullName() : null);
            dto.setServiceName(booking.getServiceName());
            dto.setBookingDate(booking.getBookingDate());
            dto.setBookingStatus(booking.getBookingStatus().name());
            dto.setCreatedAt(booking.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/payments")
    public List<PaymentDashboardDTO> getAllPayments() {
        return paymentRepository.findAll().stream().map(payment -> {
            PaymentDashboardDTO dto = new PaymentDashboardDTO();
            dto.setId(payment.getId());
            dto.setBookingId(payment.getBooking() != null ? payment.getBooking().getId() : null);
            dto.setAmount(payment.getAmount());
            dto.setPaymentStatus(payment.getPaymentStatus().name());
            dto.setPaymentMethod(payment.getPaymentMethod());
            dto.setPaymentDate(payment.getPaymentDate());
            return dto;
        }).collect(Collectors.toList());
    }
}
