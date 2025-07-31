package com.noumerica.noumericahealth.data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timeCreated;
    private LocalDateTime timeModified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeCreated() { return timeCreated; }
    public void setTimeCreated(LocalDateTime timeCreated){
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeModified() { return timeModified; }
    public void setTimeModified(LocalDateTime timeModified){
        this.timeModified = timeModified;
    }


    public void updateTimeModified() {
        this.timeModified = LocalDateTime.now();
    }
    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity that)) {
            return false; // null or not an AbstractEntity class
        }
        if (getId() != null) {
            return getId().equals(that.getId());
        }
        return super.equals(that);
    }
}
