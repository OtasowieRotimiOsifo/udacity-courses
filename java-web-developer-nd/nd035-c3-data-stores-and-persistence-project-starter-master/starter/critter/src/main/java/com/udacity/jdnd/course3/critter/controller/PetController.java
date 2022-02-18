package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.mapping.PetMapper;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private static Logger logger = LoggerFactory.getLogger(PetController.class);
    @Autowired
    private PetService petService;

    @Autowired
    private PetMapper petMapper;

    @PostMapping("/{ownerId}")
    public PetDTO savePet(@PathVariable(name="ownerId") Long ownerId, @RequestBody PetDTO petDTO) {
        logger.info("petDTO = {}", petDTO);
        Long id = Optional.ofNullable(petDTO.getId()).orElse(Long.valueOf(-1));
        ModelMapper modelMapper = new ModelMapper();
        Pet p = petService.findPet(id);
        if(p != null) {
            petMapper.updatePetFromDto(petDTO, p);
        } else {
            logger.info("petDTO = {}, id = {}", petDTO.getNotes(), id);
            //p = modelMapper.map(petDTO, Pet.class);
            p = dtoToPet(petDTO);
        }

        p = petService.save(p, ownerId);
        PetDTO pDTO = modelMapper.map(p, PetDTO.class);
        pDTO.setOwnerId(petDTO.getOwnerId());
        return pDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        ModelMapper modelMapper = new ModelMapper();
        Pet p = petService.findPet(petId);
        if(p != null) {
            PetDTO dto = modelMapper.map(p, PetDTO.class);
            dto.setOwnerId(p.getCustomer().getId());
            return dto;
        }
        return null; //throw exception?
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long customerId) {
        List<Pet> pets = petService.findPetByOwner(customerId);
        List<PetDTO> petDTOS = new ArrayList<>();
        if(pets != null) {
            ModelMapper modelMapper = new ModelMapper();
            for(Pet pet : pets) {
                PetDTO pDTO = modelMapper.map(pet, PetDTO.class);
                pDTO.setOwnerId(customerId);
                petDTOS.add(pDTO);
            }
        }
        return petDTOS;
    }

    private Pet dtoToPet(PetDTO petDTO) {
        Pet p = new Pet();
        p.setName(petDTO.getName());
        p.setBirthDate(petDTO.getBirthDate());
        p.setType(petDTO.getType());
        p.setNotes(petDTO.getNotes());
        petDTO.setOwnerId(petDTO.getOwnerId());
        return p;
    }
}
