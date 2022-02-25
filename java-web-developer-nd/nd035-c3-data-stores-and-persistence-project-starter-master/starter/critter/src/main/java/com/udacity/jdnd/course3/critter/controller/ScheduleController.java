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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = dtoToSchedule(scheduleDTO);
        Schedule s = new Schedule();

        s.setScheduleDate(scheduleDTO.getDate());
        s.setActivities(scheduleDTO.getActivities());
        s.setEmployees(userService.findEmployees(scheduleDTO.getEmployeeIds()));
        s.setPets(petService.findPets(scheduleDTO.getPetIds()));
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
        logger.info("pet id = {}", petId);
        Pet pet = petService.findPet(petId);
        List<Schedule> schedules = pet.getSchedules();
        return scheduleListToDTOList(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        logger.info("employee id = {}", employeeId);
        Employee e = userService.findEmployee(employeeId);
        List<Schedule> schedules = e.getSchedules();
        return scheduleListToDTOList(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        logger.info("customer id = {}", customerId);
        Customer c = userService.findCustomer(customerId).get();
        logger.info("customer name = {}", c.getName());
        logger.info("customers pet name = {}", c.getPets().get(0).getName());
        List<Schedule> schedules = scheduleService.getAllSchedulesForCustomer(c);
        logger.info("schedule for customer = {}", schedules.get(0).getId());
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
        Schedule schedule = new Schedule();
        schedule.setScheduleDate(scheduleDTO.getDate());
        List<Employee>  employees = new ArrayList<>();

        List<Pet>  pets = new ArrayList<>();

        for(Long petId: scheduleDTO.getPetIds()) {
            Pet pet = petService.findPet(petId);
            logger.info("loop: pet name", pet.getName());
            pets.add(pet);
        }
        schedule.setPets(pets);

        for(Long employeeId: scheduleDTO.getEmployeeIds()) {
            Employee  employee = userService.findEmployee(employeeId);
            logger.info("loop: employee name", employee.getName());
            employees.add(employee);
        }

        schedule.setEmployees(employees);

        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }
}
