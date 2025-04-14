package com.example.miniapp.repositories;

import com.example.miniapp.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    // Query to find ratings for a given entity by ID and type (e.g., captain, customer, or trip)
    List<Rating> findByEntityIdAndEntityType(Long entityId, String entityType);

    // Query to retrieve ratings with a score above a specified value
    List<Rating> findByScoreGreaterThan(Integer score);
}
