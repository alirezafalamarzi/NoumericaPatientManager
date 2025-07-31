package com.noumerica.noumericahealth.services;

import com.noumerica.noumericahealth.data.ImageGroup;
import com.noumerica.noumericahealth.data.Note;
import com.noumerica.noumericahealth.data.Scan;
import com.noumerica.noumericahealth.repository.ImageGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageGroupService {

	private final ImageGroupRepository repository;

	@Autowired
	public ImageGroupService(ImageGroupRepository repository) {
		this.repository = repository;
	}

	public Optional<ImageGroup> get(Long id) {
		return repository.findById(id);
	}

	public ImageGroup update(ImageGroup entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<ImageGroup> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<ImageGroup> list(Pageable pageable, Specification<ImageGroup> filter) {
		return repository.findAll(filter, pageable);
	}

	public int count() {
		return (int) repository.count();
	}

	public List<Note> getNotes(long imageGroupId) {
		return repository.getNotes(imageGroupId);
	}

	public List<Scan> getScanData(int imageGroupId) {
		return repository.getScans(imageGroupId);
	}


	public ImageGroup getImageGroupById(long imageGroupId) {
		return repository.getImageGroupById(imageGroupId);
	}

	public List<ImageGroup> getImageGroupByPatientId(long patientId) {
		return repository.getImageGroupsByPatientId(patientId);
	}


}
