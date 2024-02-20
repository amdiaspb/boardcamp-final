package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.RentalRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {
    
    @Autowired private TestRestTemplate restTemplate;
    @Autowired private RentalRepository rentalRepository;
    @Autowired private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDb() {
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
	void givenValidCustomer_whenCreatingCustomer_thenCreateCustomer(){
        CustomerDTO customer = new CustomerDTO("nome", "12345678901");
        HttpEntity<CustomerDTO> body = new HttpEntity<>(customer);

        ResponseEntity<CustomerModel> response = restTemplate.exchange("/customers", HttpMethod.POST, body, CustomerModel.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, customerRepository.count());
    }
}
