package com.honeacademy.petfinder.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jmaina on 8/14/17.
 */
@Entity(tableName = "images",foreignKeys = @ForeignKey(entity = Pet.class,
        parentColumns = "id",
        childColumns = "pet_id"))
public class Image {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name="pet_id")
    private long petId;
    private String size;
    private String url;
    @ColumnInfo(name="photo_id")
    private String photoId;

    public Image(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }
}

