package com.noumerica.noumericahealth.repository;


import com.noumerica.noumericahealth.data.SamplePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, Long>,  JpaSpecificationExecutor<SamplePerson> {

}
