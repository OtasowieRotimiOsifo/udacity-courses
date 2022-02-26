package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.model.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
public class EmployeeDTO {
    private long id;
    private String name;

    private Set<DayOfWeek> daysAvailable;
    private Set<EmployeeSkill> skills;

    public Set<EmployeeSkill> getSkills() {

        return this.skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {

        this.skills = skills;
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

    public Set<DayOfWeek> getDaysAvailable() {
        return this.daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {

        this.daysAvailable = daysAvailable;
    }
}
