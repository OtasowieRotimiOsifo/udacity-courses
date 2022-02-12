package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Owner;
import com.udacity.jdnd.course3.critter.service.UserService;
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
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final String []  PROPERTIES_TO_IGNORE_ON_COPY = { "id" };

    @Autowired
    private UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Long id = Optional.ofNullable(customerDTO.getId()).orElse(Long.valueOf(-1));
        Owner o = userService.findCustomer(id).orElseGet(Owner::new);
        BeanUtils.copyProperties(customerDTO, 0, PROPERTIES_TO_IGNORE_ON_COPY);
        List<Long> petIds = Optional.ofNullable(customerDTO.getPetIds()).orElseGet(ArrayList::new);
        o = userService.save(o, petIds);
        return copyCustomerToDTO(o);
    }


    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Owner> owners = userService.getAllCustomers();
        return copyCustomersToDTOs(owners);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private CustomerDTO copyCustomerToDTO(Owner o){
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(o, dto);
        o.getPets().forEach( pet -> {
            dto.getPetIds().add(pet.getId());
        });
        return dto;
    }

    private List<CustomerDTO> copyCustomersToDTOs (List<Owner> owners) {
        List dtos = new ArrayList<CustomerDTO>();
        // convert to DTO
        owners.forEach( c -> {
            dtos.add(this.copyCustomerToDTO((Owner)c));
        });
        return dtos;
    }
}
