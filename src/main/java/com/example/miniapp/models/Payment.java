package com.example.miniapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String paymentMethod; // e.g., "card", "cash"

    private Boolean paymentStatus; // true if paid

    // One payment belongs to one trip
    @OneToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
}
