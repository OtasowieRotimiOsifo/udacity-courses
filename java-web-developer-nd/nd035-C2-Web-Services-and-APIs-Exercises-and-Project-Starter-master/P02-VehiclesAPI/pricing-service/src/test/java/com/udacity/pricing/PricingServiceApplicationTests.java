package com.udacity.pricing;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	public void testGetCarPriceWithvehicleId() throws Exception {
		URI uri = new URI("/prices/19");
		mvc.perform(get(uri) // spring data rest, no controller needed
				.accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
				.andExpect(jsonPath("$.vehicleId").exists()).andExpect(jsonPath("$.vehicleId", is(19)));
	}
	
	/**
     * Test for listing all car prices in the repository.
     * @throws Exception if the read operation of the prices list fails
     */
	@Test
	public void testListCarPrices() throws Exception {
		URI uri = new URI("/prices");
		mvc.perform(get(uri).accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded").exists()).andExpect(jsonPath("$._embedded.prices").exists())
				.andExpect(jsonPath("$._embedded.prices", hasSize(25)));
	}
	
	@Test
	public void contextLoads() {
	}

}
