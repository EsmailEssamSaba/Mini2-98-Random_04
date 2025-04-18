package com.example.miniapp.services;

import com.example.miniapp.models.Payment;
import com.example.miniapp.models.Trip;
import com.example.miniapp.repositories.PaymentRepository;
import com.example.miniapp.repositories.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
private final TripRepository tripRepository;
    public PaymentService(PaymentRepository paymentRepository, TripRepository tripRepository) {
        this.paymentRepository = paymentRepository;
        this.tripRepository = tripRepository;
    }

    public Payment addPayment(Payment payment) {
        // Fetch Trip by ID from the Payment's Trip object
        Trip trip = tripRepository.findById(payment.getTrip().getId())
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        payment.setTrip(trip);

        Payment savedPayment = paymentRepository.save(payment);

        // Update the Trip's Payment reference
        trip.setPayment(savedPayment);
        tripRepository.save(trip);

        return savedPayment;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public Payment updatePayment(Long id, Payment updatedPayment) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existingPayment.setAmount(updatedPayment.getAmount());

        existingPayment.setPaymentMethod(updatedPayment.getPaymentMethod());
        existingPayment.setPaymentStatus(updatedPayment.getPaymentStatus());

        return paymentRepository.save(existingPayment);
    }


    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        Trip trip = payment.getTrip();

        if (trip != null) {
            trip.setPayment(null);
            tripRepository.save(trip);
        }

        paymentRepository.deleteById(id);
    }

    public List<Payment> findPaymentsByTripId(Long tripId) {
        return paymentRepository.findByTripId(tripId);
    }

    public List<Payment> findByAmountThreshold(Double threshold) {
        return paymentRepository.findByAmountGreaterThan(threshold);
    }
}
