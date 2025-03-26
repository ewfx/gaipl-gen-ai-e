package com.wellsfargo.incident_management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incident_1 {
    private String incidentId;
    private String priority;
    private String category;
    private String assignmentGroup;
    private String assignedTo;
    private String description;
    private String state;
    private String openedAt;
    private String resolvedAt;
    private String duration;
    private String impact;
    private String urgency;
    private String resolutionNotes;
}