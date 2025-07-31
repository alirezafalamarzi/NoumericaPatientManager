package com.noumerica.noumericahealth.data;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "note")
public class Note extends AbstractEntity {

    @Column(name = "subject")
    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "image_group_id")
    private ImageGroup imageGroup;

//    @ManyToOne
//    private Doctor madeBy;

    public String getSubject() {
        return this.subject;
    }

    public String getContent() {
        return this.content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public void setMadeBy(Doctor madeBy) {
//        this.madeBy = madeBy;
//    }

    public Note(Doctor madeBy, String subject, String content) {
//        this.madeBy = madeBy;
        this.subject = subject;
        this.content = content;
        this.setTimeCreated(LocalDateTime.now());
        this.setTimeModified(LocalDateTime.now());
    }

    public Note() {
        this.setTimeCreated(LocalDateTime.now());
        this.setTimeModified(LocalDateTime.now());
    }

    public ImageGroup getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(ImageGroup imageGroup) {
        this.imageGroup = imageGroup;
    }
}
