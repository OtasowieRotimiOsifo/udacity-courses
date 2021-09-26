package com.udacity.vehicles.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarService;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Collections;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Slf4j
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    private CarService carService;

    @MockBean
    private PriceClient priceClient;

    @MockBean
    private MapsClient mapsClient;

    private Car carInstance = null;
    
    private Long carId = null;
    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @Before
    public void setup() {
    
    	Random ran = new Random();
    	carId = ran.nextInt(100) + 100L;
    	
    	Location location = new Location(40.730610, -73.935242);
    	location.setCity("New York");
    	location.setState("New York");
    	location.setZip("1 200 200 111 NY");
    	Mockito.when(mapsClient.getAddress(location)).thenReturn(location);
    	
    	Mockito.when(priceClient.getPrice(carId)).thenReturn("2000USD");
    
    	carInstance = getCar(carId, location);
        
        given(carService.save(any())).willReturn(carInstance);
        given(carService.findById(any())).willReturn(carInstance);
        given(carService.list()).willReturn(Collections.singletonList(carInstance));
    }

    /**
     * Tests for successful creation of new car in the system
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void createCar() throws Exception {
        Car car = getCar();
        mvc.perform(
                post(new URI("/cars"))
                        .content(json.write(car).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     * @throws Exception if the read operation of the vehicle list fails
     */
	@Test
	public void listCars() throws Exception {
		
		Car actual = carService.list().get(0);
		Car expected = carInstance;
		
		log.info("city = {}", actual.getLocation().getCity());
		log.info("car price = {}", actual.getPrice());
		log.info("car model = {}", actual.getDetails().getModel());
		log.info("Id = {}", actual.getId());
		
		Assert.assertEquals(actual, expected);
		
		URI uri = new URI("/cars");
		 mvc.perform(get(uri)
	                .accept(MediaType.APPLICATION_JSON_UTF8))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$._embedded").exists())
	                .andExpect(jsonPath("$._embedded.carList", hasSize(1)))
	                .andExpect(jsonPath("$._embedded.carList[0].id", is(carId.intValue())))
	                .andExpect(jsonPath("$._embedded.carList[0].price", is("2000USD")))
	                .andExpect(jsonPath("$._embedded.carList[0].location.city", is("New York")))
	                .andExpect(jsonPath("$._embedded.carList[0].location.state", is("New York")))
	                .andExpect(jsonPath("$._embedded.carList[0].location.zip", is("1 200 200 111 NY")))
	                .andExpect(jsonPath("$._embedded.carList[0].condition", is(Condition.USED.name())));
	}

    /**
     * Tests the read operation for a single car by ID.
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void findCar() throws Exception {
    	
    	Car actual = carService.findById(carId);
		Car expected = carInstance;
		log.info("city = {}", actual.getLocation().getCity());
		log.info("car price = {}", actual.getPrice());
		log.info("car model = {}", actual.getDetails().getModel());
		log.info("Id = {}", actual.getId());
		Assert.assertEquals(actual, expected);
		
    	String carIdAsStr = carId.toString();
    	URI uri = new URI("/cars/" + carIdAsStr);
    	
    	log.info("Path Variable in find by Id = {}", uri.getPath());
		
		  mvc.perform(get(uri)
		  .accept(MediaType.APPLICATION_JSON_UTF8)) 
		  .andExpect(status().isOk())
		  .andExpect(jsonPath("$.id", is(carId.intValue())))
		  .andExpect(jsonPath("$.price", is("2000USD")))
		  .andExpect(jsonPath("$.location.city", is("New York")))
		  .andExpect(jsonPath("$.location.state", is("New York")))
		  .andExpect(jsonPath("$.location.zip", is("1 200 200 111 NY")))
		  .andExpect(jsonPath("$.condition", is(Condition.USED.name())));
		 
    }

    /**
     * Tests the deletion of a single car by ID.
     * @throws Exception if the delete operation of a vehicle fails
     */
	@Test
	public void deleteCar() throws Exception {
		
		String carIdAsStr = carId.toString();
		URI uri = new URI("/cars/" + carIdAsStr);

		log.info("Path Variable in DELETE = {}", uri.getPath());
		mvc.perform(delete(uri).accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent());
	}

    /**
     * Creates an example Car object for use in testing.
     * @return an example Car object
     */
    private Car getCar() {
        Car car = getCarWithDetails();
        
        car.setLocation(new Location(40.730610, -73.935242));
        
        return car;
    }
     
    /**
     * Creates an example Car object wth location and car id for use in testing.
     * @return an example Car object wth location and car id
     */
    private Car getCar(Long id, Location location) {
        Car car = getCar();
        car.setId(id);
      
        String price = priceClient.getPrice(id);
        log.info("price in = {}", price);
        car.setPrice(price);
        
        Location address = mapsClient.getAddress(location);
        log.info("city address in = {}", address.getCity());
        car.setLocation(address);
        
        return car;
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