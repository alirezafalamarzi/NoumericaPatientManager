package com.noumerica.noumericahealth.services;

import com.noumerica.noumericahealth.data.ImageGroup;
import com.noumerica.noumericahealth.data.Note;
import com.noumerica.noumericahealth.data.Scan;
import com.noumerica.noumericahealth.repository.ImageGroupRepository;
import com.noumerica.noumericahealth.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

	private final NoteRepository repository;

	@Autowired
	public NoteService(NoteRepository repository) {
		this.repository = repository;
	}

	public Optional<Note> get(Long id) {
		return repository.findById(id);
	}

	public Note update(Note entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<Note> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Note> list(Pageable pageable, Specification<Note> filter) {
		return repository.findAll(filter, pageable);
	}

	public int count() {
		return (int) repository.count();
	}

}
