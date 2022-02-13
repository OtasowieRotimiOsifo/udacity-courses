package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    @Transactional
    public Customer save(Customer c, List<Long>petIds) {
        for (Long petId : petIds) {
            Pet p = petRepository.getPetById(petId);
            if(p != null) {
                c.addPet(p);
            }
        }

        return customerRepository.save(c);
    }

    public Optional<Customer> findCustomer(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
