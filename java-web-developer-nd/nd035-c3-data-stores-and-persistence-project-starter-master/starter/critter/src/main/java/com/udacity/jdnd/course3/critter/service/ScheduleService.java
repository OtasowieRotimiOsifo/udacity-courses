package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule save(Schedule s) {return scheduleRepository.save(s);}

    public List<Schedule> findByPet(Pet pet) {
        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> findByEmployee(Employee e) {
        return scheduleRepository.getAllByEmployeesContains(e);
    }

    public List<Schedule> getAllSchedulesForCustomer(Customer customer) {
        return scheduleRepository.getAllByPetsIn(customer.getPets());
    }

    public Optional<Schedule> findSchedule(Long id) {
        return scheduleRepository.findById(id);
    }
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
