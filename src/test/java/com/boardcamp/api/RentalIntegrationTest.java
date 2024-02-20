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

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentalIntegrationTest {
    
    @Autowired private TestRestTemplate restTemplate;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RentalRepository rentalRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDb(){
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
	void givenValidCustomer_whenCreatingCustomer_thenCreateCustomer(){
        GameModel Game = new GameModel(null,"name", "image", 3, 500);
        GameModel createdGame = gameRepository.save(Game);

        CustomerModel customer = new CustomerModel(null, "name", "12345678910");
        CustomerModel createdCustomer = customerRepository.save(customer);

        RentalDTO rental = new RentalDTO(3, createdCustomer.getId(), createdGame.getId());
        HttpEntity<RentalDTO> body = new HttpEntity<>(rental);

        ResponseEntity<RentalModel> response = restTemplate.exchange("/rentals", HttpMethod.POST, body, RentalModel.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, rentalRepository.count());
    }
}
