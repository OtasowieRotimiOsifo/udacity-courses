package com.udacity.vehicles;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.vehicles.api.CarControllerTest;
import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VehiclesApiApplicationTests {
	@Autowired
	private PriceClient priceClient;
	
	@Autowired
	private MapsClient mapsClient;
	
	@Autowired
	private CarService carService;
	
    @Test
    public void contextLoads() {
    }

    @Test
    public void testPriceClient() {
    	String price = priceClient.getPrice(1l);
    	Assert.assertNotNull(price);
    	log.info("Price: {}", price);
    }
    
    @Test
    public void testMapsClient() {
    	Location location = new Location(40.730610, -73.935242);
    	Location address = mapsClient.getAddress(location);
    	Assert.assertNotNull(address);
    	Assert.assertNotNull( address.getLat());
    	Assert.assertNotNull( address.getLon());
    	Assert.assertNotNull( address.getCity());
    	Assert.assertNotNull( address.getState());
    	Assert.assertNotNull( address.getZip());
    	Assert.assertNotNull( address.getAddress());
    	log.info("Address: {}", address.getAddress());
    	log.info("Lat: {}, Long: {}, city: {}, state: {}", address.getLat(), address.getLon(), address.getCity(), address.getState());
    }
    
    @Test
    public void testSaveCar() {
    	Car car = getCarWithDetails();
    	Car savedCar = carService.save(car);
    	Assert.assertNotNull(savedCar);
    	Assert.assertNotNull(savedCar.getPrice());
    	Assert.assertNotNull(savedCar.getLocation().getAddress());
    }
    
    /**
     * Creates an example car with details
     * @return an example Car object
     */
    private Car getCarWithDetails() {
        Car car = new Car();
      
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}
