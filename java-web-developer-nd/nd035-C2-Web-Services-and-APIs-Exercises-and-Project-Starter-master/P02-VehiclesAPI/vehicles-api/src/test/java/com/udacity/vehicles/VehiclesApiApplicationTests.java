package com.udacity.vehicles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.vehicles.api.CarControllerTest;
import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class VehiclesApiApplicationTests {
	@Autowired
	private PriceClient priceClient;
	
	@Autowired
	private MapsClient mapsClient;
	
    @Test
    public void contextLoads() {
    }

    @Test
    public void testPriceClient() {
    	String price = priceClient.getPrice(1l);
    	log.info("Price: {}", price);
    }
    
    @Test
    public void testMapsClient() {
    	Location location = new Location(40.730610, -73.935242);
    	Location address = mapsClient.getAddress(location);
    	log.info("Address: {}", address.getAddress());
    	log.info("Lat: {}, Long: {}, city: {}, state: {}", address.getLat(), address.getLon(), address.getCity(), address.getState());
    }
}
