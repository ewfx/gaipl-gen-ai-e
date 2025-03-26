package com.wellsfargo.incident_management.service;

import com.wellsfargo.incident_management.dto.IncidentDTO;
import com.wellsfargo.incident_management.mapper.IncidentMapper;
import com.wellsfargo.incident_management.model.Incident;
import com.wellsfargo.incident_management.repository.IncidentRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
//    private final EmbeddingService embeddingService;
    private final VectorStore vectorStore;
    private final IncidentMapper incidentMapper;

    @Value("${csv.file}")
    private String csvFilePath;

    public IncidentService(IncidentRepository incidentRepository, VectorStore vectorStore, IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.vectorStore = vectorStore;
        this.incidentMapper = incidentMapper;
    }

    public List<IncidentDTO> getAllIncidents() {
        List<IncidentDTO> incidents = new ArrayList<>();

        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                IncidentDTO incident = new IncidentDTO(
                        record.get("Incident ID"),              // Map header correctly
                        record.get("Priority"),
                        record.get("Category"),
                        record.get("Assignment Group"),
                        record.get("Assigned To"),
                        record.get("Description"),
                        record.get("State"),
                        record.get("Opened At"),
                        record.get("Resolved At"),
                        record.get("Duration"),
                        record.get("Impact"),
                        record.get("Urgency"),
                        record.get("Resolution Notes")
                );
                incidents.add(incident);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incidents;
    }

    /**
     * Saves incident into PostgreSQL with embeddings.
     */
    public void saveIncident(IncidentDTO incident) {
//        float[] embedding = embeddingService.generateEmbedding(
//                incident.getDescription() + " " + incident.getResolutionNotes());
//
//        Incident dbIncident = new Incident();
//        dbIncident.setIncidentId(incident.getIncidentId());
//        dbIncident.setDescription(incident.getDescription());
//        dbIncident.setResolutionNotes(incident.getResolutionNotes());
//        dbIncident.setEmbedding(embedding);

//        incidentRepository.save(dbIncident);
        this.vectorStore.add(List.of(incident).stream().map(IncidentMapper::convertToDocument).toList());
        System.out.println("Saved Resolved Incident: " + incident.getIncidentId());
    }

    // Method to check if an incident exists in the vector store
    public boolean incidentExists(String incidentId) {
        List<Document> results = vectorStore.similaritySearch(incidentId);  // Check if the ID exists

        if (!results.isEmpty()) {
            return results.stream().anyMatch(doc -> doc.getMetadata().get("incidentId").equals(incidentId));
        }

        return false;
    }

    public List<IncidentDTO> getPastIncidents(String incidentId) {
        List<IncidentDTO> allIncidents = getAllIncidents();
        List<IncidentDTO> filtered = new ArrayList<>();

        for (IncidentDTO incident : allIncidents) {
            if (!incident.getIncidentId().equals(incidentId) && filtered.size() < 5) {
                filtered.add(incident);
            }
        }
        return filtered;
    }

    public List<IncidentDTO> findRelated(String incidentDetails) {
        List<Document> results = this.vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(incidentDetails)
                        .topK(10)                        // Fetch more initially to avoid missing relevant incidents
                        .similarityThreshold(0.5)
                        .build()
        );

        // Use a Set to avoid duplicates based on incident ID
        Set<String> uniqueIncidentIds = new HashSet<>();

        // Convert and filter duplicates
        List<IncidentDTO> uniqueResults = results.stream()
                .map(IncidentMapper::convertToIncidentDTO)
                .filter(incident -> uniqueIncidentIds.add(incident.getIncidentId()))  // Only add unique IDs
                .collect(Collectors.toList());

        // Limit the output to 5 results
        return uniqueResults.stream().limit(5).collect(Collectors.toList());
    }
}
