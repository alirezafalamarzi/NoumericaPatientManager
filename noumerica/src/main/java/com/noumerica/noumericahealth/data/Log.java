package com.noumerica.noumericahealth.data;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log extends AbstractEntity {
    @Column(name = "time_accessed")
    private LocalDateTime timeAccessed;
    @ManyToOne
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Patient patientAccessed;

    @Enumerated(EnumType.STRING)
    private Operation operation;


    public Log() {}
    public Log(Patient patientAccessed, Operation operation) {
        this.patientAccessed = patientAccessed;
        this.operation = operation;
        this.timeAccessed = LocalDateTime.now();
    }

    public LocalDateTime getTimeAccessed() {
        return this.timeAccessed;
    }
    public Patient getPatientAccessed() {
        return this.patientAccessed;
    }
    public Operation getOperation() {
        return this.operation;
    }

}
