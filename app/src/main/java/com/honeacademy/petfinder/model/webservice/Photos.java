package com.honeacademy.petfinder.model.webservice;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class Photos {
    @ElementList(required = true, inline = true)
    private List<PetPhotoType> photos;

    public List<PetPhotoType> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PetPhotoType> photos) {
        this.photos = photos;
    }
}
