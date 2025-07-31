package com.noumerica.noumericahealth.services;

import com.noumerica.noumericahealth.data.Doctor;
import com.noumerica.noumericahealth.data.Patient;
import com.noumerica.noumericahealth.data.Scan;
import com.noumerica.noumericahealth.repository.DoctorRepository;
import com.noumerica.noumericahealth.repository.PatientRepository;
import com.noumerica.noumericahealth.repository.ScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScanService {

	private final ScanRepository repository;

	@Autowired
	public ScanService(ScanRepository repository) {
		this.repository = repository;
	}

	public Optional<Scan> get(Long id) {
		return repository.findById(id);
	}

	public Scan update(Scan entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<Scan> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Scan> list(Pageable pageable, Specification<Scan> filter) {
		return repository.findAll(filter, pageable);
	}

	public int count() {
		return (int) repository.count();
	}
}
