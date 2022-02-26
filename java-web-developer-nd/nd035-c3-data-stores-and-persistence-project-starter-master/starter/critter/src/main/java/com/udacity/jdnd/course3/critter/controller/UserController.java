package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.mapping.CustomerMapper;
import com.udacity.jdnd.course3.critter.mapping.EmployeeMapper;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.modelmapper.ModelMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

        Long id = Optional.ofNullable(customerDTO.getId()).orElse(Long.valueOf(-1));
        ModelMapper modelMapper = new ModelMapper();
        Optional<Customer> op = userService.findCustomer(id);
        Customer c = null;
        if (op.isPresent()) {
            c = op.get();
            customerMapper.updateCustomerFromDto(customerDTO, c);
        } else {
            c = modelMapper.map(customerDTO, Customer.class);
        }
        c = userService.save(c);
        return modelMapper.map(c, CustomerDTO.class);
    }


    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = userService.getAllCustomers();
        return copyCustomersToDTOs(customers);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {

        Pet pet = petService.findPet(petId);
        if (pet != null) {
            Customer c = pet.getCustomer();
            return copyCustomerToDTO(c);
        }
        return null;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Long id = Optional.ofNullable(employeeDTO.getId()).orElse(Long.valueOf(-1));
        ModelMapper modelMapper = new ModelMapper();
        Employee e = userService.findEmployee(id);
        if (e != null) {
            employeeMapper.updateEmployeeFromDto(employeeDTO, e);
        } else {
            e = modelMapper.map(employeeDTO, Employee.class);
        }

        e.setEmployeeSkills(employeeDTO.getSkills());
        e.setDaysAvailable(employeeDTO.getDaysAvailable());
        e.setName(employeeDTO.getName());

        Employee savedEmployee = userService.save(e);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        ModelMapper modelMapper = new ModelMapper();
        Employee employee = userService.findEmployee(employeeId);
        if (employee != null) {
            return modelMapper.map(employee, EmployeeDTO.class);
        }
        return null; //throw exception?
    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = userService.findEmployee(employeeId);
        employee.setDaysAvailable(daysAvailable);
        Employee savedEmployee = userService.save(employee);
        ModelMapper modelMapper = new ModelMapper();
        EmployeeDTO dto = modelMapper.map(savedEmployee, EmployeeDTO.class);
        dto.setSkills(savedEmployee.getEmployeeSkills());
        return dto;
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = userService.findAvailableEmployees(employeeDTO.getSkills(), employeeDTO.getDate());
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        if (employees != null) {
            for (Employee e : employees) {
                EmployeeDTO employeeDTOLoc = new EmployeeDTO();
                employeeDTOLoc.setSkills(e.getEmployeeSkills());
                employeeDTOLoc.setDaysAvailable(e.getDaysAvailable());
                employeeDTOLoc.setName(e.getName());
                employeeDTOLoc.setId(e.getId());
                employeeDTOs.add(employeeDTOLoc);
            }
        }
        return employeeDTOs;
    }

    private CustomerDTO copyCustomerToDTO(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(c, dto);
        for (Pet pet : c.getPets()) {
            dto.getPetIds().add(pet.getId());
        }
        return dto;
    }

    private List<CustomerDTO> copyCustomersToDTOs(List<Customer> customers) {
        List dtoArrayList = new ArrayList<CustomerDTO>();
        // convert to DTO
        for (Customer c : customers) {
            dtoArrayList.add(this.copyCustomerToDTO((Customer) c));
        }
        return dtoArrayList;
    }
}
