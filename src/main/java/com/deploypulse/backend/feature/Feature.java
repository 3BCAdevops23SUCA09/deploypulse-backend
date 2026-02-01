package com.deploypulse.backend.feature;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String owner;

    @Enumerated(EnumType.STRING)
    private FeatureStatus status;

    @Enumerated(EnumType.STRING)
    private BuildStatus buildStatus;

    private Boolean approved;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
