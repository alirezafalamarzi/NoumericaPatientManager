package com.noumerica.noumericahealth.repository;

import com.noumerica.noumericahealth.data.ImageGroup;
import com.noumerica.noumericahealth.data.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
}
