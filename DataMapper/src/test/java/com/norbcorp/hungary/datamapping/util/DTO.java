package com.norbcorp.hungary.datamapping.util;

import java.util.Date;
import java.util.List;

/**
 * Created by nor on 2017.05.06..
 */
public class DTO {
    private String name;
    private Integer age;
    private Date dateOfRegistration;
    private String description=null;
    private List<String> strings=null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}