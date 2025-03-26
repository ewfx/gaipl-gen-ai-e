package com.wellsfargo.incident_management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.SQLType;

@Entity
@Getter
@Setter
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "incident_id", unique = true, nullable = false)
    private String incidentId;

    @Column(name = "priority")
    private String priority;

    @Column(name = "category")
    private String category;

    @Column(name = "assignment_group")
    private String assignmentGroup;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "state")
    private String state;

    @Column(name = "opened_at")
    private String openedAt;

    @Column(name = "resolved_at")
    private String resolvedAt;

    @Column(name = "duration")
    private String duration;

    @Column(name = "impact")
    private String impact;

    @Column(name = "urgency")
    private String urgency;

    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;

    //Embedding vector for similarity search
    @Column(name = "embedding", columnDefinition = "vector(384)")
    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 384)
    private float[] embedding;
}
