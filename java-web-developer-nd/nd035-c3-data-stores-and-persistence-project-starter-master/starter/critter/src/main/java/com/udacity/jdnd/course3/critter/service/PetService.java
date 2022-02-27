package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet save(Pet p, Long ownerId) {
        Optional<Customer> op = customerRepository.findById(ownerId);
        if(op.isPresent()) {
            Customer c = op.get();

            p.setCustomer(c);
            Pet savedPet = petRepository.save(p);

            c.addPet(savedPet);
            customerRepository.save(c);
            return savedPet;
        }
        return null;
    }
    public Pet findPet(Long petId) {
        Optional<Pet> op = petRepository.findById(petId);
        if(op.isPresent()) {
            return op.get();
        }
        return null;
    }

    public List<Pet> findPets(List<Long> petIds) {
        List<Pet>  pets = new ArrayList<>();;
        for(Long petId: petIds) {
            Pet pet = findPet(petId);
            if(null != pet) {
                pets.add(pet);
            }
        }
        return pets;
    }

    public List<Pet> findPetByOwner(Long customerId) {
        return petRepository.findByCustomerId(customerId);
    }

    public List<Pet> findPetAll() {
        return petRepository.findAll();
    }
}
