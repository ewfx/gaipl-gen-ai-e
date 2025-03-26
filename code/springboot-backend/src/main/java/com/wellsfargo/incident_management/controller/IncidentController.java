package com.wellsfargo.incident_management.controller;

import com.wellsfargo.incident_management.dto.IncidentDTO;
import com.wellsfargo.incident_management.model.Incident;
import com.wellsfargo.incident_management.model.Incident_1;
import com.wellsfargo.incident_management.service.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService service;

    public IncidentController(IncidentService service) {
        this.service = service;
    }

    @GetMapping
    public List<IncidentDTO> getAllIncidents() {
        return service.getAllIncidents();
    }

    @GetMapping("/past")
    public List<IncidentDTO> getPastIncidents(@RequestParam String incidentId) {
        return service.getPastIncidents(incidentId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addIncident(@RequestBody IncidentDTO incident) {
        service.saveIncident(incident);
        return ResponseEntity.ok("Incident saved successfully");
    }

    @GetMapping("/related")
    public ResponseEntity<List<IncidentDTO>> getRelatedIncidents(@RequestParam String incidentDescription) {
        List<IncidentDTO> related = service.findRelated(incidentDescription);
        return ResponseEntity.ok(related);
    }
}
