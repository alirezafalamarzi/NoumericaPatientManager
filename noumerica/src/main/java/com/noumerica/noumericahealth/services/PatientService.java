package com.noumerica.noumericahealth.services;

import com.noumerica.noumericahealth.data.Patient;
import com.noumerica.noumericahealth.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public Optional<Patient> get(Long id) {
        return repository.findById(id);
    }

    public Patient update(Patient entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Patient> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Patient> getPatients() {
        return repository.findAll();
    }

    /**
     * Get top 20 patients by timestamp
     * @return date entry of top 20 patients.
     */
    public List<Patient> getRecentPatients() {
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by("createdDate").descending());
        return repository.findAll(pageRequest).getContent();

//    public Page<Patient> list(Pageable pageable, Specification<Patient> filter) {
//        return repository.findAll(filter, pageable);
//    }

//    public int count() {
//        return (int) repository.count();
//    }

}
// no usage so im just commenting for now
//    public Patient findByFirstName(String firstName) {
//        return repository.findByFirstName(firstName);
//    }
}
