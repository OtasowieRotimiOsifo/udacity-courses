package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.model.PetType;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
public class PetDTO {
    private long id;
    private PetType type;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;

    public PetType getType() {

        return this.type;
    }

    public void setType(PetType type) {

        this.type = type;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public long getOwnerId() {

        return this.ownerId;
    }

    public void setOwnerId(long ownerId) {

        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {

        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {

        return this.notes;
    }

    public void setNotes(String notes) {

        this.notes = notes;
    }

    public long getId() {

        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
