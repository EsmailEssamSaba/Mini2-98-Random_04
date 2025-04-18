package com.example.miniapp.controllers;


import com.example.miniapp.models.Captain;
import com.example.miniapp.models.Customer;
import com.example.miniapp.models.Payment;
import com.example.miniapp.models.Trip;
import com.example.miniapp.repositories.CaptainRepository;
import com.example.miniapp.repositories.CustomerRepository;
import com.example.miniapp.repositories.PaymentRepository;
import com.example.miniapp.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final TripService tripService;
    private final CustomerRepository customerRepository;
    private final CaptainRepository captainRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public TripController(TripService tripService , CustomerRepository customerRepository, CaptainRepository captainRepository, PaymentRepository paymentRepository) {
        this.tripService = tripService;
        this.customerRepository = customerRepository;
        this.captainRepository = captainRepository;
        this.paymentRepository = paymentRepository;

    }

    @PostMapping("/addTrip")
    public Trip addTrip(@RequestBody Map<String, Object> request) {

        Long captainId = Long.valueOf(request.get("captainId").toString());
        Long customerId = Long.valueOf(request.get("customerId").toString());
        Long paymentId = request.get("paymentId") != null ? Long.valueOf(request.get("paymentId").toString()) : null;

        LocalDateTime tripDate = LocalDateTime.parse(request.get("tripDate").toString());
        String origin = request.get("origin").toString();
        String destination = request.get("destination").toString();
        Double tripCost = Double.valueOf(request.get("tripCost").toString());

        Captain captain = captainRepository.findById(captainId)
                .orElseThrow(() -> new RuntimeException("Captain not found"));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Trip trip = new Trip();
        trip.setTripDate(tripDate);
        trip.setOrigin(origin);
        trip.setDestination(destination);
        trip.setTripCost(tripCost);
        trip.setCaptain(captain);
        trip.setCustomer(customer);


        if (paymentId != null) {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));
            trip.setPayment(payment);
            payment.setTrip(trip);
        }

        return tripService.addTrip(trip);
    }

    @GetMapping("/allTrips")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @PutMapping("/update/{id}")
    public Trip updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        return tripService.updateTrip(id, trip);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return "Trip with ID " + id + " has been canceled.";
    }

    @GetMapping("/findByDateRange")
    public List<Trip> findTripsWithinDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return tripService.findTripsWithinDateRange(startDate, endDate);
    }

    @GetMapping("/findByCaptainId")
    public List<Trip> findTripsByCaptainId(@RequestParam Long captainId) {
        return tripService.findTripsByCaptainId(captainId);
    }
}
