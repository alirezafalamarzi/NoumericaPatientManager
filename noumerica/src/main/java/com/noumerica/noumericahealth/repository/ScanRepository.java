package com.noumerica.noumericahealth.repository;

import com.noumerica.noumericahealth.data.ImageGroup;
import com.noumerica.noumericahealth.data.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ScanRepository extends JpaRepository<Scan, Long>, JpaSpecificationExecutor<Scan> {
}
