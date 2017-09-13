//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.11 at 03:48:33 PM EAT 
//


package com.honeacademy.petfinder.model.webservice;



import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;



@Root(name="pet")
public class PetfinderPetRecord {

    @Element(name="id")
    protected Long id;
    @Element(name = "shelterId",required = false)
    protected String shelterId;
    @Element(name = "shelterPetId", required = false)
    protected String shelterPetId;
    @Element(name = "name",required = false)
    protected String name;
    @Element(name = "animal",required = false)
    protected String animal;
    @ElementList(name = "breeds",required = false)
    protected List<String> breeds;
    @Element(name="mix",required = false)
    protected String mix;
    @Element(name = "age",required = false)
    protected String age;
    @Element(name = "lastUpdate",required = false)
    protected String lastUpdate;
    @Element(name = "sex",required = false)
    protected String sex;
    @Element(name = "size",required = false)
    protected String size;
    @ElementList(name = "options",required = false)
    protected List<String> options;
    @Element(name = "description",required = false)
    protected String description;
    @Element(name = "lastUpString",required = false)
    protected String lastUpString;
    @Element(name = "status",required = false)
    protected String status;
    @Element(name = "media",required = false)
    protected Media media;
    @Element(name = "contact",required = false)
    protected PetContactType contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShelterId() {
        return shelterId;
    }

    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }

    public String getShelterPetId() {
        return shelterPetId;
    }

    public void setShelterPetId(String shelterPetId) {
        this.shelterPetId = shelterPetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public List<String> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<String> breeds) {
        this.breeds = breeds;
    }

    public String getMix() {
        return mix;
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpString() {
        return lastUpString;
    }

    public void setLastUpString(String lastUpString) {
        this.lastUpString = lastUpString;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public PetContactType getContact() {
        return contact;
    }

    public void setContact(PetContactType contact) {
        this.contact = contact;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}