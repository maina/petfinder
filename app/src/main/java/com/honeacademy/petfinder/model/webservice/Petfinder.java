package com.honeacademy.petfinder.model.webservice;


import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="petfinder",strict = false)
@Default(required=false)
public class Petfinder {

    @Element(name="header")
    protected PetfinderHeaderType header;
    @Element(name="lastOffset")
    protected Integer lastOffset;
    @ElementList(name="pets")
    protected List<PetfinderPetRecord> pets;

    public PetfinderHeaderType getHeader() {
        return header;
    }

    public void setHeader(PetfinderHeaderType header) {
        this.header = header;
    }

    public Integer getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(Integer lastOffset) {
        this.lastOffset = lastOffset;
    }

    public List<PetfinderPetRecord> getPets() {
        return pets;
    }

    public void setPets(List<PetfinderPetRecord> pets) {
        this.pets = pets;
    }



}
