package com.noumerica.noumericahealth.data;

import com.vaadin.flow.component.html.Image;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageGroup is the class for a group of images for a single scan
 * For example if a brain scan has images from different sections of the brain,
 * these images are considered as an image group for the brain scan
 */
@Entity
@Table(name = "image_group")
public class ImageGroup extends AbstractEntity {

    /**
     * Title of the image group for example : Brain Scan 11/04/2012
     */
    @Column(name = "title")
    private String title;

    /**
     * Description including the diagnosis for the scan
     */
    @Column(name = "description")
    private String description;

    /**
     * Number of images in a image group
     */
    @Column(name = "number_of_images")
    private int numberOfImages;

    /**
     * The notes for this image group
     */
    @OneToMany(cascade = {
            CascadeType.ALL
    })
    @JoinColumn(name = "image_group_id")
    private List<Note> notes;

    /**
     * The images (scans) included in this image group
     */
    @OneToMany(cascade = {
            CascadeType.ALL
    })
    @JoinColumn(name = "image_group_id")
    private List<Scan> scans;

    /**
     * The patient who has this image group
     */
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    public List<Note> getNotes() {
        return this.notes;
    }
    public List<Scan> getScans() {
        return this.scans;
    }

    public void addNote(Note note) {
        if(this.notes == null) {
            this.notes = new ArrayList<>();
        }
        notes.add(note);
    }

    public void addScan(Scan scan) {
        if(this.scans == null) {
            this.scans = new ArrayList<>();
        }
        scans.add(scan);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfImages() {
        return numberOfImages;
    }

    public void setNumberOfImages(int numberOfImages) {
        this.numberOfImages = numberOfImages;
    }
}
