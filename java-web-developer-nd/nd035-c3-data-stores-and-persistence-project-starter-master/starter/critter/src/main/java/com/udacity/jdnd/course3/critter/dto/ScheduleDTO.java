package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.model.EmployeeSkill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
public class ScheduleDTO {
    private long id;
    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate date;
    private Set<EmployeeSkill> activities;

    public ScheduleDTO() {
        employeeIds = new ArrayList<>();
        petIds = new ArrayList<>();
        activities = new HashSet<>();
    }

    public List<Long> getEmployeeIds() {

        return this.employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }
    public void addEmployeeId(Long employeeId) {
        this.employeeIds.add(employeeId);
    }

    public List<Long> getPetIds() {
        return
                this.petIds;
    }
    public void addPetId(Long petId) {

        this.petIds.add(petId);
    }

    public void setPetIds(List<Long> petIds) {

        this.petIds = petIds;
    }

    public LocalDate getDate() {

        return this.date;
    }

    public void setDate(LocalDate date) {

        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {

        return this.activities;
    }

    public void addEmployeeSkill(EmployeeSkill skill) {

        this.activities.add(skill);
    }

    public void setActivities(Set<EmployeeSkill> activities) {

        this.activities = activities;
    }
}
