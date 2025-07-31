package com.noumerica.noumericahealth.data;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

/**
 * The admin class for an admin user
 */
@Entity
@Table(name="admin")
public class Admin extends Person {

    /**
     * List of doctors
     */
    @OneToMany
    private List<Doctor> searchedDoctors;

    /**
     * List of patients
     */
    @OneToMany
    private List<Patient> searchedPatients;

    /**
     * List of logs
     */
    @OneToMany
    private List<Log> searchedLogs;

    public Admin(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public Admin(){}

    public List<Doctor> getDoctors() {
        return this.searchedDoctors;
    }
    public List<Patient> getPatients() {
        return this.searchedPatients;
    }

    public void addDoctor(Doctor doctor) {
    }

    public void addPatient(Patient patient) {
    }

    public Doctor removeDoctor() {
        return new Doctor();
    }

    public Patient removePatient() {
        return new Patient();
    }

    public List<Log> getLogs() {
        // For all doctors get logs
        return this.searchedLogs;
    }
}

