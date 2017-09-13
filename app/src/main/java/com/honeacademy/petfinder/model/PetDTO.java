package com.honeacademy.petfinder.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by jmaina on 8/14/17.
 */
public class PetDTO extends Pet {


    private String imageUrl;
    @Embedded
    private Contact contact;

    //    parentColumn refers to Embedded Contact table's id column,
//    entityColumn refers to Pet table's userId (User - Pet relation) column,
//    entity refers to table(Pet) which has relation with User table.
//    @Relation(parentColumn = "pet_id_contact", entityColumn = "id", entity = Pet.class)
//    public List<Pet> pets;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}

