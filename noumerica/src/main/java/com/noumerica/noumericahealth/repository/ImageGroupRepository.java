package com.noumerica.noumericahealth.repository;

import com.noumerica.noumericahealth.data.ImageGroup;
import com.noumerica.noumericahealth.data.Note;
import com.noumerica.noumericahealth.data.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageGroupRepository extends JpaRepository<ImageGroup, Long>, JpaSpecificationExecutor<ImageGroup> {
//    @Query("select group from ImageGroup group " +
//            "where lower(group.id) like lower(concat('%', :searchTerm, '%')) " +
//            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
//    List<Contact> search(@Param("searchTerm") String searchTerm);
    @Query("SELECT n FROM Note n WHERE n.imageGroup.id=:id")
    List<Note> getNotes(@Param("id") long id);

    @Query("SELECT s FROM Scan s WHERE s.imageGroup.id=:id")
    List<Scan> getScans(@Param("id") long id);

    @Query("SELECT ig FROM ImageGroup ig WHERE ig.patient.id=:id")
    List<ImageGroup> getImageGroupsByPatientId(@Param("id") long id);


    @Query("SELECT ig FROM ImageGroup ig WHERE ig.id=:id")
    ImageGroup getImageGroupById(@Param("id") long id);

}
