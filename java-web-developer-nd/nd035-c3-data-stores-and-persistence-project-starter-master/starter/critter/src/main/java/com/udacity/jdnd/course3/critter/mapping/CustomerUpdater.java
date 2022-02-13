package com.udacity.jdnd.course3.critter.mapping;

import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import org.mapstruct.MappingTarget;

public class CustomerUpdater {
    public static void updateCustomerFromDto(CustomerDTO dto, Customer c) {
        if(dto.getName() != null) {
            c.setName(dto.getName());
        }
        if(dto.getPhoneNumber() != null) {
            c.setPhoneNumber(dto.getPhoneNumber());
        }
        if(dto.getNotes() != null) {
            c.setNotes(dto.getNotes());
        }

    }
}
