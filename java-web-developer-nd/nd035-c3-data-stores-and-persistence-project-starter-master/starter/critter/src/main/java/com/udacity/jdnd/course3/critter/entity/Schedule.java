package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Schedule {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate scheduleDate;

    @ManyToMany(targetEntity = Employee.class)
    @JoinTable(
            name = "schedule_employee",
            joinColumns = { @JoinColumn(name = "schedule_id")},
            inverseJoinColumns = { @JoinColumn(name = "employee_id")}
    )
    private List<Employee>  employees = new ArrayList<>();
    public void addEmployee(Employee employee) {

        this.employees.add(employee);
    }

    public List<Employee> getEmployees() {

        return this.employees;
    }

    public void setEmployees(List<Employee> employees) {

        this.employees = employees;
    }

    public LocalDate getScheduleDate() {

        return this.scheduleDate;
    }

    public Set<EmployeeSkill> getActivities() {

        return this.activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {

        this.activities = activities;
    }

    public void setScheduleDate(LocalDate scheduleDate) {

        this.scheduleDate = scheduleDate;
    }

    @ManyToMany(targetEntity = Pet.class)
    @JoinTable(
            name = "schedule_pet",
            joinColumns = { @JoinColumn(name = "schedule_id")},
            inverseJoinColumns = { @JoinColumn(name = "pet_id")}
    )
    private List<Pet>  pets = new ArrayList<>();;
    public void addPet(Pet pet) {

        this.pets.add(pet);
    }

    public List<Pet> getPets() {

        return this.pets;
    }

    public void setPets(List<Pet> pets) {

        this.pets = pets;
    }

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name="activities")
    @Enumerated(EnumType.STRING)
    @Column(name="activities", nullable = false)
    private Set<EmployeeSkill> activities;

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
