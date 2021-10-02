package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.reactive.function.client.WebClient;
/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
@Slf4j
public class CarService {

    private final CarRepository repository;
    
    private MapsClient mapsClient;
    
    private PriceClient priceClient;
    
    public CarService(CarRepository repository, WebClient maps, WebClient pricing, ModelMapper mapper) {
    	this.mapsClient = new MapsClient(maps, mapper);
    	this.priceClient = new PriceClient(pricing);
        this.repository = repository;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
    	Car car = this.repository.findById(id).orElseThrow(CarNotFoundException :: new);
       
       
        String priceStr = this.priceClient.getPrice(car.getId());
        log.info("priceStr = {}", priceStr);
        car.setPrice(priceStr);
       
        Location address = this.mapsClient.getAddress(car.getLocation());
        car.setLocation(address);
       
        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
    	log.info("Car in save: {}", car.getPrice());
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }
        log.info("before save");
        Car savedCar = repository.save(car);
        log.info("saved car: {}", savedCar.getId());
        return savedCar;
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
    	Car car = this.repository.findById(id).orElseThrow(CarNotFoundException :: new);
    	
    	this.repository.deleteById(id); 	
    }
}
