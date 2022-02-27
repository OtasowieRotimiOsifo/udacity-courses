package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.model.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Customer save(Customer c) {
        return customerRepository.save(c);
    }

    public Employee save(Employee e) {
        return employeeRepository.save(e);
    }

    public Optional<Customer> findCustomer(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Employee findEmployee(Long id) {
        Optional<Employee> op = employeeRepository.findById(id);
        if(op.isPresent()) {
            return op.get();
        }
        return null;
    }

    public List<Employee> findAvailableEmployees(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> employees = employeeRepository.getAllByDaysAvailableContains(date.getDayOfWeek());
        return employees.stream()
                .filter(employee -> employee.getEmployeeSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

    public List<Employee> findEmployees(List<Long> employeeIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        return employees;
    }

    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }
}
