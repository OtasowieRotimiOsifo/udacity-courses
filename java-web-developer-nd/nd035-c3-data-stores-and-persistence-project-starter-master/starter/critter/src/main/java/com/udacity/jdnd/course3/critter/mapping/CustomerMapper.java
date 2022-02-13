package com.udacity.jdnd.course3.critter.mapping;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO mapDtoFromCustomer(Customer entity);
    Customer mapCustomerFromDto(CustomerDTO dto);
}
