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
        Schedule schedule = dtoToSchedule(scheduleDTO);
        Schedule savedSchedule = scheduleService.save(schedule);

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
        List<Schedule> schedules = scheduleService.findByPet(pet);
        return scheduleListToDTOList(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee e = userService.findEmployee(employeeId);
        List<Schedule> schedules = scheduleService.findByEmployee(e);
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
        for(Employee e: s.getEmployees()) {
            scheduleDTO.getEmployeeIds().add(e.getId());
        }

        for(Pet p: s.getPets()) {
            scheduleDTO.getPetIds().add(p.getId());
        }

        scheduleDTO.setActivities(s.getActivities());
        return scheduleDTO;
    }

    private Schedule dtoToSchedule(ScheduleDTO scheduleDTO) {

        ModelMapper modelMapper = new ModelMapper();
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

        schedule.setScheduleDate(scheduleDTO.getDate());

        List<Employee>  employees = new ArrayList<>();

        List<Pet>  pets = new ArrayList<>();

        for(Long petId: scheduleDTO.getPetIds()) {
            Pet pet = petService.findPet(petId);
            pets.add(pet);
        }
        schedule.setPets(pets);

        for(Long employeeId: scheduleDTO.getEmployeeIds()) {
            Employee  employee = userService.findEmployee(employeeId);
            employees.add(employee);
        }

        schedule.setEmployees(employees);

        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }
}
