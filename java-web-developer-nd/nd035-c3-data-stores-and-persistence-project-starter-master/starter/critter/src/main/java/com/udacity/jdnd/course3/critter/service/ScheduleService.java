package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Schedule save(Schedule s) {return scheduleRepository.save(s);}

    public List<Schedule> findByPets(Pet pet) {
        return scheduleRepository.findByPets(pet);
    }

    public List<Schedule> findByEmployee(Employee e) {
        return scheduleRepository.findByEmployees(e);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
