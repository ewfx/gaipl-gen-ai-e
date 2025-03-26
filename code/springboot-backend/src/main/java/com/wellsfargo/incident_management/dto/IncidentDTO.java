package com.wellsfargo.incident_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDTO {
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
