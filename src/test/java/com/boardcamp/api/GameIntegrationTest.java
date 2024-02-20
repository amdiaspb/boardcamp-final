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

import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameIntegrationTest {
    
    @Autowired private TestRestTemplate restTemplate;
    @Autowired private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDb(){
        gameRepository.deleteAll();
    }

    @Test
	void givenValidGame_whenCreatingGame_thenCreateGame(){
        GameDTO game = new GameDTO("name", "image", 3, 1500);
        HttpEntity<GameDTO> body = new HttpEntity<>(game);

        ResponseEntity<GameModel> response = restTemplate.exchange("/games", HttpMethod.POST, body, GameModel.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, gameRepository.count());
    }
}
