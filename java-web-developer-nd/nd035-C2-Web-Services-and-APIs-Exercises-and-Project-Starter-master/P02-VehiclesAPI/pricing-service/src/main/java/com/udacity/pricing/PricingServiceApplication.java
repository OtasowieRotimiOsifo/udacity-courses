package com.udacity.pricing;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.udacity.pricing.domain.price.PriceRepository;
import com.udacity.pricing.service.PricingService;

/**
 * Creates a Spring Boot Application to run the Pricing Service.
 */
@SpringBootApplication
@EnableEurekaClient
public class PricingServiceApplication {
	
	@Autowired
	private PriceRepository priceRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }
    
    //stackoverflow
    @Bean
    InitializingBean sendDatabase() {
        return () -> {
        	priceRepository.saveAll(PricingService.PRICES);
          };
       }
}
