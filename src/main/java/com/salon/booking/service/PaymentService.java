package com.salon.booking.service;

import com.salon.booking.dto.PaymentRequestDTO;
import com.salon.booking.entity.Booking;
import com.salon.booking.entity.Payment;
import com.salon.booking.entity.Schedule;
import com.salon.booking.exception.ResourceNotFoundException;
import com.salon.booking.repository.BookingRepository;
import com.salon.booking.repository.PaymentRepository;
import com.salon.booking.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public String processPayment(PaymentRequestDTO request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Payment payment = paymentRepository.findByBookingId(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment tagihan not found"));

        if (booking.getBookingStatus() != Booking.BookingStatus.PENDING_PAYMENT) {
            return "Pembayaran sudah diproses atau booking dibatalkan.";
        }

        if (request.isPaymentSuccess()) {
            // Sukses
            payment.setPaymentStatus(Payment.PaymentStatus.SUCCESS);
            payment.setPaymentMethod(request.getPaymentMethod());
            payment.setPaymentDate(LocalDateTime.now());
            
            booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
            
            paymentRepository.save(payment);
            bookingRepository.save(booking);
            return "Pembayaran Sukses. Booking telah di-Confirm!";
        } else {
            // Gagal / Cancel
            payment.setPaymentStatus(Payment.PaymentStatus.FAILED);
            payment.setPaymentDate(LocalDateTime.now());
            
            booking.setBookingStatus(Booking.BookingStatus.CANCELLED);
            
            // Kembalikan slot
            Schedule schedule = booking.getSchedule();
            schedule.setAvailableSlot(schedule.getAvailableSlot() + 1);
            
            paymentRepository.save(payment);
            bookingRepository.save(booking);
            scheduleRepository.save(schedule);
            
            return "Pembayaran Gagal. Slot dikembalikan.";
        }
    }
}
