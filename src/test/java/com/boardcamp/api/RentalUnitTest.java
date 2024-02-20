package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.RentalRepository;
import com.boardcamp.api.services.RentalService;

@SpringBootTest
public class RentalUnitTest {

    @InjectMocks RentalService rentalService;
    @Mock private RentalRepository rentalRepository;
    @Mock private CustomerRepository customerRepository;

    @Test
    void givenCustomrNotExist_whenCreatingrental_thenThrowsError(){

        RentalDTO rental = new RentalDTO(1, 1L, 3L);
        doReturn(Optional.empty()).when(customerRepository).findById(any());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> rentalService.save(rental));
        
        assertNotNull(exception);
        assertEquals("Customer not found!", exception.getMessage());
        verify(rentalRepository, times(0)).save(any());
        verify(customerRepository, times(1)).findById(any());
    }
}