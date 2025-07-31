package com.noumerica.noumericahealth.services;

import com.noumerica.noumericahealth.data.*;
import com.noumerica.noumericahealth.data.Doctor;
import com.noumerica.noumericahealth.data.Log;
import com.noumerica.noumericahealth.repository.DoctorRepository;
import com.noumerica.noumericahealth.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void logOperation(Patient patient, Operation operation) {
        Log log = new Log(patient, operation);
        logRepository.save(log);
    }

    public List<Log> getRecentLogs() {
        return logRepository.findAllByOrderByTimeAccessedDesc();
    }
}

//	private final LogRepository repository;
//
//	@Autowired
//	public LogService(LogRepository repository) {
//		this.repository = repository;
//	}
//
//	public Optional<Log> get(Long id) {
//		return repository.findById(id);
//	}
//
//	public Log update(Log entity) {
//		return repository.save(entity);
//	}
//
//	public void delete(Long id) {
//		repository.deleteById(id);
//	}
//
//	public Page<Log> list(Pageable pageable) {
//		return repository.findAll(pageable);
//	}
//
//	public Page<Log> list(Pageable pageable, Specification<Log> filter) {
//		return repository.findAll(filter, pageable);
//	}
//
//	public int count() {
//		return (int) repository.count();
//	}
//
//
//}
