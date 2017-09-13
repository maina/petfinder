package com.honeacademy.petfinder.model.webservice;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "contact", strict = false)
public class PetContactType {

    @Element(name = "name", required = false)
    protected String name;
    @Element(name = "address1", required = false)
    protected String address1;
    @Element(name = "address2", required = false)
    protected String address2;
    @Element(name = "city", required = false)
    protected String city;
    @Element(name = "state")
    protected String state;
    @Element(name = "zip")
    protected String zip;
    @Element(name = "phone", required = false)
    protected String phone;
    @Element(name = "fax", required = false)
    protected String fax;
    @Element(name = "email", required = false)
    protected String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
