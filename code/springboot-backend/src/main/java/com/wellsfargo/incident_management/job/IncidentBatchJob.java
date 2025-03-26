package com.wellsfargo.incident_management.job;

import com.wellsfargo.incident_management.dto.IncidentDTO;
import com.wellsfargo.incident_management.service.IncidentService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
public class IncidentBatchJob {
    @Value("${csv.file}")
    private String csvFilePath;

    private final IncidentService incidentService;

    public IncidentBatchJob(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    // Run the batch job every 1 hour
    @Scheduled(fixedRate = 3600000)  // 1 hour = 3600000 ms
    @PostConstruct
    public void runBatchJob() {
        System.out.println("Running batch job...");

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
                if ("Resolved".equalsIgnoreCase(incident.getState()) && !incidentService.incidentExists(incident.getIncidentId())) {
                    incidentService.saveIncident(incident);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
