package com.noumerica.noumericahealth.data;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor extends Person {

    @Column(name = "username")
    private String userName;
    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patients;

    @OneToMany(cascade = {
            CascadeType.ALL
    })
    @JoinColumn(name = "doctor_id")
    private List<Log> logs;

    @Enumerated(EnumType.STRING)
    private ProfessionalType type;


    public Doctor() {}
    public Doctor(String firstName, String lastName, String userName, ProfessionalType type) {
        super(firstName, lastName);
        this.type = type;
    }
    public List<Patient> getPatients() {
        return this.patients;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public ProfessionalType getType() {
        return this.type;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
