package com.noumerica.noumericahealth.services;

import com.noumerica.noumericahealth.data.Doctor;
import com.noumerica.noumericahealth.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

	private final DoctorRepository repository;

	@Autowired
	public DoctorService(DoctorRepository repository) {
		this.repository = repository;
	}

	public Optional<Doctor> get(Long id) {
		return repository.findById(id);
	}

	public Doctor update(Doctor entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<Doctor> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Doctor> list(Pageable pageable, Specification<Doctor> filter) {
		return repository.findAll(filter, pageable);
	}

	public int count() {
		return (int) repository.count();
	}


}
