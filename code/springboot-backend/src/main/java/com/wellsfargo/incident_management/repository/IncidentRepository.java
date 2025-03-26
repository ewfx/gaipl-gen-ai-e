package com.wellsfargo.incident_management.repository;

import com.wellsfargo.incident_management.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    @Query(value = """
        SELECT * FROM incidents
        ORDER BY embedding <=> :queryEmbedding
        LIMIT 5
    """, nativeQuery = true)
    List<Incident> findRelatedIncidents(float[] queryEmbedding);
}
