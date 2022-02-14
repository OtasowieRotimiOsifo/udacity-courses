package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Pet save(Pet e) {
        return petRepository.save(e);
    }

    public Pet save(Pet p, Long ownerId) {
        Optional<Customer> op = customerRepository.findById(ownerId);
        if(op.isPresent()) {
            Customer c = op.get();
            p.setCustomer(c);

            c.addPet(p);
            customerRepository.save(c);

            return petRepository.save(p);
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

    public List<Pet> findPetByOwner(Customer c) {
        return petRepository.findByCustomer(c);
    }
}
