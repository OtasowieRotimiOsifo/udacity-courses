package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Owner;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.OwnerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetRepository petRepository;

    @Transactional
    public Owner save(Owner o, List<Long>petIds) {
        o.getPets().clear();
        for (Long petId : petIds) {
            Pet p = petRepository.getPetById(petId);
            if(p != null) {
                o.getPets().add(p);
            }
        }

        return ownerRepository.save(o);
    }

    public Optional<Owner> findCustomer(Long id) {
        return ownerRepository.findById(id);
    }

    public List<Owner> getAllCustomers() {
        return ownerRepository.findAll();
    }
}
