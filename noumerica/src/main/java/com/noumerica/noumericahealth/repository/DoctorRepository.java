package com.noumerica.noumericahealth.repository;

import com.noumerica.noumericahealth.data.Doctor;
import com.noumerica.noumericahealth.data.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor>  {

}
