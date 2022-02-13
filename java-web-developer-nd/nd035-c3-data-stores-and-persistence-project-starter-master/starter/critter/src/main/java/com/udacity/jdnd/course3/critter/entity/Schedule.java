package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(targetEntity = Employee.class)
    private List<Employee>  employee;

    @ManyToMany(targetEntity = Pet.class)
    private List<Pet>  pets;

    private LocalDate scheduleDate;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name="activities")
    @Enumerated(EnumType.STRING)
    @Column(name="activities", nullable = false)
    private Set<EmployeeSkill> activities;
}
