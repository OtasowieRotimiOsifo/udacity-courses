package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.model.PetType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pet")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="pet", nullable = false)
    private PetType type;

    private LocalDate birthDate;

    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable=false, updatable=false)
    private Owner owner;
}
