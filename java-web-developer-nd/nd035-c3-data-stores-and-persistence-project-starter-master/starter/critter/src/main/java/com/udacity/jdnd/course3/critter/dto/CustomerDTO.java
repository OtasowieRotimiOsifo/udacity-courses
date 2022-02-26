package com.udacity.jdnd.course3.critter.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
public class CustomerDTO {
    private long id;
    private String name;
    private String phoneNumber;
    private String notes;
    private List<Long> petIds;

    public CustomerDTO() {

        this.petIds = new ArrayList<>();
    }
    public long getId() {

        return this.id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPhoneNumber() {

        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {

        return this.notes;
    }

    public void setNotes(String notes) {

        this.notes = notes;
    }

    public List<Long> getPetIds() {

        return this.petIds;
    }

    public void setPetIds(List<Long> petIds) {

        this.petIds = petIds;
    }
}
