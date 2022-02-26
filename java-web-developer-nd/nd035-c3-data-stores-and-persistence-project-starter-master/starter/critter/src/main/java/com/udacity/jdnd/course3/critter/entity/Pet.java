package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.model.PetType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="pet", nullable = false)
    private PetType type;

    @Column
    private LocalDate birthDate;

    @Column
    private String notes;

    public LocalDate getBirthDate() {

        return this.birthDate;
    }

    public String getNotes() {

        return this.notes;
    }

    public void setNotes(String notes) {

        this.notes = notes;
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @ManyToMany(
            mappedBy = "pets")
    private List<Schedule> schedules = new ArrayList<>();
    public void addSchedule(Schedule s) {

        schedules.add(s);
    }

    public void setBirthDate(LocalDate birthDate) {

        this.birthDate = birthDate;
    }

    public Customer getCustomer() {

        return this.customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable=false)
    private Customer customer;

    public Long getId() {

        return this.id;
    }

    public PetType getType() {

        return this.type;
    }

    public void setType(PetType type) {

        this.type = type;
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
}
