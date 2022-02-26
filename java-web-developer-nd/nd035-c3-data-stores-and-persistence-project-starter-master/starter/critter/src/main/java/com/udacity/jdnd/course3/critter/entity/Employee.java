package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name="days_available")
    @Enumerated(EnumType.STRING)
    @Column(name="daysAvailable", nullable = false)
    private Set<DayOfWeek> daysAvailable;

    @ElementCollection
    @CollectionTable(name="skills")
    @Enumerated(EnumType.STRING)
    @Column(name="skills", nullable = false)
    private Set<EmployeeSkill> employeeSkills;

    @ManyToMany(
            mappedBy = "employees")
    private List<Schedule> schedules = new ArrayList<>();
    public void addSchedule(Schedule s) {
        schedules.add(s);
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

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

    public Set<EmployeeSkill> getEmployeeSkills() {

        return this.employeeSkills;
    }

    public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {

        this.employeeSkills = employeeSkills;
    }
}
