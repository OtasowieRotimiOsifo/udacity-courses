package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule s = new Schedule();

        s.setScheduleDate(scheduleDTO.getDate());
        s.setActivities(scheduleDTO.getActivities());
        s.setEmployees(userService.findEmployees(scheduleDTO.getEmployeeIds()));
        s.setPets(petService.findPets(scheduleDTO.getPetIds()));
        Schedule savedSchedule = scheduleService.save(s);

        for(Pet pet : s.getPets()) {
            pet.addSchedule(s);
            petService.save(pet, pet.getCustomer().getId());
        }

        for(Employee employee : s.getEmployees()) {
            employee.addSchedule(s);
            userService.save(employee);
        }

        return scheduleToDto(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> schedules = scheduleService.getAllSchedules();
        return scheduleListToDTOList(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.findPet(petId);
        List<Schedule> schedules = pet.getSchedules();
        return scheduleListToDTOList(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee e = userService.findEmployee(employeeId);
        List<Schedule> schedules = e.getSchedules();
        return scheduleListToDTOList(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer c = userService.findCustomer(customerId).get();
        List<Schedule> schedules = scheduleService.getAllSchedulesForCustomer(c);
        return scheduleListToDTOList(schedules);
    }

    private List<ScheduleDTO> scheduleListToDTOList(List<Schedule> schedules) {
        List< ScheduleDTO> dtoList = new ArrayList<>();
        for(Schedule schedule : schedules) {
            dtoList.add(scheduleToDto(schedule));
        }
        return dtoList;
    }

    private ScheduleDTO scheduleToDto(Schedule s) {
        ModelMapper modelMapper = new ModelMapper();
        ScheduleDTO scheduleDTO = modelMapper.map(s, ScheduleDTO.class);

        for (Employee e : s.getEmployees()) {
            scheduleDTO.addEmployeeId(e.getId());
        }


        for (Pet p : s.getPets()) {
            scheduleDTO.addPetId(p.getId());
        }


        scheduleDTO.setActivities(s.getActivities());
        return scheduleDTO;
    }
}
