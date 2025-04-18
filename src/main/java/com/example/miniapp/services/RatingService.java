package com.example.miniapp.services;

import com.example.miniapp.models.Captain;
import com.example.miniapp.models.Rating;
import com.example.miniapp.repositories.CaptainRepository;
import com.example.miniapp.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final CaptainRepository captainRepository;

    public RatingService(RatingRepository ratingRepository, CaptainRepository captainRepository) {
        this.ratingRepository = ratingRepository;
        this.captainRepository = captainRepository;
    }

    public Rating addRating(Rating rating) {
        Rating savedRating = ratingRepository.save(rating);
        if (Objects.equals(savedRating.getEntityType(), "Captain")) {
            Long captainId=rating.getEntityId();
            List<Rating> ratings = ratingRepository.findByEntityIdAndEntityType(captainId, rating.getEntityType());
            double totalRating = 0;
            for (Rating rating1 : ratings) {
                totalRating += rating1.getScore();
            }

            double avgRating = totalRating / ratings.size();

            Captain captain = captainRepository.findById(captainId)
                    .orElseThrow(() -> new RuntimeException("Captain not found"));

            captain.setAvgRatingScore(avgRating);
            captainRepository.save(captain);




        }

        return ratingRepository.save(rating);
    }

    public Rating updateRating(String id, Rating updatedRating) {
        // Check if the rating exists by its id
        if (!ratingRepository.existsById(id)) {
            throw new RuntimeException("Rating not found");
        }

        // Retrieve the existing rating using the id
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        Rating prevRating = optionalRating.orElseThrow(() -> new RuntimeException("Rating not found"));

        // Update fields if not null in the updated rating
        if (updatedRating.getRatingDate() != null) {
            prevRating.setRatingDate(updatedRating.getRatingDate());
        }

        // Update other fields as needed
        if (updatedRating.getScore() != null) {
            prevRating.setScore(updatedRating.getScore());
        }
        if (updatedRating.getComment() != null) {
            prevRating.setComment(updatedRating.getComment());
        }
        if (updatedRating.getEntityId() != null) {
            prevRating.setEntityId(updatedRating.getEntityId());
        }
        if (updatedRating.getEntityType() != null) {
            prevRating.setEntityType(updatedRating.getEntityType());
        }

        prevRating.setId(id);
        if (Objects.equals(prevRating.getEntityType(), "Captain")) {
            Long captainId=prevRating.getEntityId();
            List<Rating> ratings = ratingRepository.findByEntityIdAndEntityType(captainId, prevRating.getEntityType());
            double totalRating = 0;
            for (Rating rating1 : ratings) {
                totalRating += rating1.getScore();
            }

            double avgRating = totalRating / ratings.size();

            Captain captain = captainRepository.findById(captainId)
                    .orElseThrow(() -> new RuntimeException("Captain not found"));

            captain.setAvgRatingScore(avgRating);
            captainRepository.save(captain);




        }

        return ratingRepository.save(prevRating);
    }


    public void deleteRating(String id) {
        ratingRepository.deleteById(id);
    }

    public List<Rating> getRatingsByEntity(Long entityId, String entityType) {
        return ratingRepository.findByEntityIdAndEntityType(entityId, entityType);
    }

    public List<Rating> findRatingsAboveScore(int minScore) {
        return ratingRepository.findByScoreGreaterThan(minScore);
    }
}
