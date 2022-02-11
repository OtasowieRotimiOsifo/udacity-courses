package com.udacity.jdnd.course3.critter.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "owner")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Owner {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL, targetEntity = Pet.class)
    @Column(nullable = false)
    private List<Pet> pets;
    public void addPet(Pet pet) {
        pets.add(pet);
    }
}
