package com.wellsfargo.incident_management.mapper;

import com.wellsfargo.incident_management.dto.IncidentDTO;
import com.wellsfargo.incident_management.model.Incident;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IncidentMapper {

    /**
     * Maps Incident entity to IncidentDTO.
     */
    public IncidentDTO toDTO(Incident incident) {
        if (incident == null) {
            return null;
        }

        IncidentDTO dto = new IncidentDTO();
        dto.setIncidentId(incident.getIncidentId());
        dto.setPriority(incident.getPriority());
        dto.setCategory(incident.getCategory());
        dto.setAssignmentGroup(incident.getAssignmentGroup());
        dto.setAssignedTo(incident.getAssignedTo());
        dto.setDescription(incident.getDescription());
        dto.setState(incident.getState());
        dto.setOpenedAt(incident.getOpenedAt());
        dto.setResolvedAt(incident.getResolvedAt());
        dto.setDuration(incident.getDuration());
        dto.setImpact(incident.getImpact());
        dto.setUrgency(incident.getUrgency());
        dto.setResolutionNotes(incident.getResolutionNotes());

        return dto;
    }

    /**
     * Maps IncidentDTO back to Incident entity (if needed).
     */
    public Incident toEntity(IncidentDTO dto) {
        if (dto == null) {
            return null;
        }

        Incident incident = new Incident();
        incident.setIncidentId(dto.getIncidentId());
        incident.setPriority(dto.getPriority());
        incident.setCategory(dto.getCategory());
        incident.setAssignmentGroup(dto.getAssignmentGroup());
        incident.setAssignedTo(dto.getAssignedTo());
        incident.setDescription(dto.getDescription());
        incident.setState(dto.getState());
        incident.setOpenedAt(dto.getOpenedAt());
        incident.setResolvedAt(dto.getResolvedAt());
        incident.setDuration(dto.getDuration());
        incident.setImpact(dto.getImpact());
        incident.setUrgency(dto.getUrgency());
        incident.setResolutionNotes(dto.getResolutionNotes());

        return incident;
    }

    public static Document convertToDocument(IncidentDTO incidentDTO) {
        // Prepare metadata map
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("incidentId", incidentDTO.getIncidentId());
        metadata.put("priority", incidentDTO.getPriority());
        metadata.put("category", incidentDTO.getCategory());
        metadata.put("assignmentGroup", incidentDTO.getAssignmentGroup());
        metadata.put("assignedTo", incidentDTO.getAssignedTo());
        metadata.put("state", incidentDTO.getState());
        metadata.put("openedAt", incidentDTO.getOpenedAt());
        metadata.put("resolvedAt", incidentDTO.getResolvedAt());
        metadata.put("duration", incidentDTO.getDuration());
        metadata.put("impact", incidentDTO.getImpact());
        metadata.put("urgency", incidentDTO.getUrgency());
        metadata.put("resolutionNotes", incidentDTO.getResolutionNotes());

        // Combine textual content for embeddings
        String text = incidentDTO.getDescription();

        // Create and return Document
        return new Document(text, metadata);
    }

    public static IncidentDTO convertToIncidentDTO(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }

        Map<String, Object> metadata = document.getMetadata();

        IncidentDTO dto = new IncidentDTO();
        dto.setIncidentId(getString(metadata, "incidentId"));
        dto.setPriority(getString(metadata, "priority"));
        dto.setCategory(getString(metadata, "category"));
        dto.setAssignmentGroup(getString(metadata, "assignmentGroup"));
        dto.setAssignedTo(getString(metadata, "assignedTo"));
        dto.setDescription(document.getText());
        dto.setState(getString(metadata, "state"));
        dto.setOpenedAt(getString(metadata, "openedAt"));
        dto.setResolvedAt(getString(metadata, "resolvedAt"));
        dto.setDuration(getString(metadata, "duration"));
        dto.setImpact(getString(metadata, "impact"));
        dto.setUrgency(getString(metadata, "urgency"));
        dto.setResolutionNotes(getString(metadata, "resolutionNotes"));

        return dto;
    }

    private static String getString(Map<String, Object> metadata, String key) {
        Object value = metadata.get(key);
        return value != null ? value.toString() : null;
    }
}
