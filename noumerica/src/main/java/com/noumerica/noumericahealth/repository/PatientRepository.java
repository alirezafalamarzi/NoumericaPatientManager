package com.noumerica.noumericahealth.repository;


import com.noumerica.noumericahealth.data.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * A patient repository class for PatientService, stores and retrieves from mysql
 * tables.
 *
 */
public interface PatientRepository extends JpaRepository<Patient, Long>,  JpaSpecificationExecutor<Patient> {

}
