package com.wellsfargo.incident_management.service;

import com.wellsfargo.incident_management.dto.IncidentDTO;
import com.wellsfargo.incident_management.model.Incident;
import com.wellsfargo.incident_management.model.Recommendation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    private final IncidentService incidentService;

    public RecommendationService(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    public List<Recommendation> getRecommendations(String incidentDescription) {
        List<IncidentDTO> pastIncidents = incidentService.findRelated(incidentDescription);
        List<Recommendation> recommendations = new ArrayList<>();

        for (IncidentDTO incident : pastIncidents) {
            recommendations.add(new Recommendation(
                    incident.getIncidentId(),
                    incident.getResolutionNotes()
            ));
        }
        return recommendations.subList(0, Math.min(5, recommendations.size()));
    }
}
