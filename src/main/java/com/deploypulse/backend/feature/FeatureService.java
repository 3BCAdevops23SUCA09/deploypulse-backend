package com.deploypulse.backend.feature;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    // CREATE
    public Feature createFeature(Feature feature) {
        feature.setCreatedAt(LocalDateTime.now());
        feature.setUpdatedAt(LocalDateTime.now());
        feature.setBuildStatus(BuildStatus.NOT_RUN);
        feature.setApproved(false);
        return featureRepository.save(feature);
    }

    // READ - all
    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    // READ - by id
    public Feature getFeatureById(Long id) {
        return featureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found"));
    }

    // UPDATE
    public Feature updateFeature(Long id, Feature updatedFeature) {
        Feature existingFeature = getFeatureById(id);

        existingFeature.setName(updatedFeature.getName());
        existingFeature.setDescription(updatedFeature.getDescription());
        existingFeature.setOwner(updatedFeature.getOwner());
        existingFeature.setStatus(updatedFeature.getStatus());
        existingFeature.setBuildStatus(updatedFeature.getBuildStatus());
        existingFeature.setApproved(updatedFeature.getApproved());
        existingFeature.setUpdatedAt(LocalDateTime.now());

        return featureRepository.save(existingFeature);
    }

    // DELETE
    public void deleteFeature(Long id) {
        featureRepository.deleteById(id);
    }
}
