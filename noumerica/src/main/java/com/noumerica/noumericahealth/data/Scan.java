package com.noumerica.noumericahealth.data;

import jakarta.persistence.*;

@Entity
@Table(name = "scan")
public class Scan extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "image_group_id")
    private ImageGroup imageGroup;

    @Lob
    @Column(name = "data")
    private byte[] data;

    public byte[] getData() {
        return this.data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }

    public ImageGroup getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(ImageGroup imageGroup) {
        this.imageGroup = imageGroup;
    }
}
