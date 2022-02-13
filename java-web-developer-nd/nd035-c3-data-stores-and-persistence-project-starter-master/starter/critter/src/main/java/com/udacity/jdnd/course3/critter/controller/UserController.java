package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.mapping.CustomerMapper;
import com.udacity.jdnd.course3.critter.mapping.CustomerUpdater;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.mapstruct.factory.Mappers;
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

    CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper .class);

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Long id = Optional.ofNullable(customerDTO.getId()).orElse(Long.valueOf(-1));

        Optional<Customer> op = userService.findCustomer(id);
        Customer c = null;
        if(op.isPresent()) {
            c = op.get();
            CustomerUpdater.updateCustomerFromDto(customerDTO, c);
        }

        ModelMapper modelMapper = new ModelMapper();
        c = modelMapper.map(customerDTO, Customer.class);
        List<Long> petIds = Optional.ofNullable(customerDTO.getPetIds()).orElseGet(ArrayList::new);
        c = userService.save(c, petIds);
        return customerMapper.mapDtoFromCustomer(c);
    }


    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = userService.getAllCustomers();
        return copyCustomersToDTOs(customers);
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

    private CustomerDTO copyCustomerToDTO(Customer c){
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(c, dto);
        c.getPets().forEach( pet -> {
            dto.getPetIds().add(pet.getId());
        });
        return dto;
    }

    private List<CustomerDTO> copyCustomersToDTOs (List<Customer> customers) {
        List dtoArrayList = new ArrayList<CustomerDTO>();
        // convert to DTO
        customers.forEach(c -> {
            dtoArrayList.add(this.copyCustomerToDTO((Customer)c));
        });
        return dtoArrayList;
    }
}
