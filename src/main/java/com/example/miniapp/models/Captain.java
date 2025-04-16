package com.example.miniapp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Captain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    private Double avgRatingScore;

    @OneToMany(mappedBy = "captain")
    private List<Trip> trips;


    public Captain() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Double getAvgRatingScore() {
        return avgRatingScore;
    }

    public void setAvgRatingScore(Double avgRatingScore) {
        this.avgRatingScore = avgRatingScore;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
