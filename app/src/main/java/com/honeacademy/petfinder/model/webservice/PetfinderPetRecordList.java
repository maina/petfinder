//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.11 at 03:48:33 PM EAT 
//


package com.honeacademy.petfinder.model.webservice;


import org.simpleframework.xml.Element;

import java.util.List;



public class PetfinderPetRecordList {

    @Element(name="pet")
    protected List<PetfinderPetRecord> pet;

    public List<PetfinderPetRecord> getPet() {
        return pet;
    }

    public void setPet(List<PetfinderPetRecord> pet) {
        this.pet = pet;
    }




}
