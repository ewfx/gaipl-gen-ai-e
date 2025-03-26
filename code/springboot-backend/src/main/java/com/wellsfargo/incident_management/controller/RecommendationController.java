package com.wellsfargo.incident_management.controller;

import com.wellsfargo.incident_management.model.Recommendation;
import com.wellsfargo.incident_management.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Recommendation> getRecommendations(@RequestParam String incidentDescription) {
        return service.getRecommendations(incidentDescription);
    }
}
