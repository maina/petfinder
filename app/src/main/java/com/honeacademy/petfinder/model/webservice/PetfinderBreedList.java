//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.11 at 03:48:33 PM EAT 
//


package com.honeacademy.petfinder.model.webservice;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;



public class PetfinderBreedList {

    @ElementList(name = "breed")
    protected List<String> breed;
    @Element(name = "animal")
    protected String animal;


    public List<String> getBreed() {
        if (breed == null) {
            breed = new ArrayList<String>();
        }
        return this.breed;
    }

    /**
     * Gets the value of the animal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnimal() {
        return animal;
    }

    /**
     * Sets the value of the animal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnimal(String value) {
        this.animal = value;
    }

}
