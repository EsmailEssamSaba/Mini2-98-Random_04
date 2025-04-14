package com.example.miniapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime tripDate;

    private String origin;

    private String destination;

    private Double tripCost;

    // Many trips can belong to one captain
    @ManyToOne
    @JoinColumn(name = "captain_id", nullable = false)
    private Captain captain;

    // Many trips can belong to one customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // One trip has one payment
    @OneToOne(mappedBy = "trip")
    private Payment payment;
}
