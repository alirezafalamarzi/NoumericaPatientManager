package com.noumerica.noumericahealth.data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "patient")
public class Patient extends Person {

    @Column(name = "health_number")
    private String healthNumber;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private List<ImageGroup> imageGroups;

//    public Patient(String firstName, String lastName, String userName) {
//        super(firstName,lastName,userName);
//    }
    @ManyToMany(cascade = {
            CascadeType.ALL
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "doctor_patient",
    joinColumns = @JoinColumn(name = "patient_id"),
    inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private List<Doctor> doctors;

    public String getHealthNumber() {
        return healthNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Puts a timestamp on patient, so we can get
     * 20 recent patients in our dashboard view.
     * @return timestamp of patient creation
     */

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<ImageGroup> getImageGroups() {
        return imageGroups;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setHealthNumber(String healthNumber) {
        this.healthNumber = healthNumber;
    }

    public void add(ImageGroup imageGroup) {
        if(this.imageGroups == null) {
            imageGroups = new ArrayList<>();
        }
        imageGroups.add(imageGroup);
    }
}
