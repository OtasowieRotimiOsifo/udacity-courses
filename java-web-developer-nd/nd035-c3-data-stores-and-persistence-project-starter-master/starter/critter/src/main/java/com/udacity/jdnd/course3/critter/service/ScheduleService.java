package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule save(Schedule s) {
        return scheduleRepository.save(s);
    }

    public List<Schedule> getAllSchedulesForCustomer(Customer customer) {
        return scheduleRepository.getAllByPetsIn(customer.getPets());
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
