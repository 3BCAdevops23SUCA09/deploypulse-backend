package com.deploypulse.backend.feature;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/features")
@CrossOrigin(origins = "*")
public class FeatureController {

    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    // CREATE
    @PostMapping
    public Feature createFeature(@RequestBody Feature feature) {
        return featureService.createFeature(feature);
    }

    // READ - all
    @GetMapping
    public List<Feature> getAllFeatures() {
        return featureService.getAllFeatures();
    }

    // READ - by id
    @GetMapping("/{id}")
    public Feature getFeatureById(@PathVariable Long id) {
        return featureService.getFeatureById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Feature updateFeature(@PathVariable Long id,
                                 @RequestBody Feature feature) {
        return featureService.updateFeature(id, feature);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
    }
}
