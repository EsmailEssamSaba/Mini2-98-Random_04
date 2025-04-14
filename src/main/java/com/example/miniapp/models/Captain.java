package com.example.miniapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Captain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    private Double avgRatingScore;
    @OneToMany(mappedBy = "captain")
    private ArrayList<Trip> trips;
}
