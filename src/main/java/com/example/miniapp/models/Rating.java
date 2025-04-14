package com.example.miniapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ratings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rating {

    @Id
    private String id;

    private Long entityId;       // ID of the rated entity (Captain, Customer, or Trip)
    private String entityType;   // e.g., "captain", "customer", "trip"
    private Integer score;       // Rating score (1-5)
    private String comment;
    private LocalDateTime ratingDate;
}
