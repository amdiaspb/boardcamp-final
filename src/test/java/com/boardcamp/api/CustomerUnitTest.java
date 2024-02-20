package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.CustomerCpfConflictException;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.services.CustomerService;

@SpringBootTest
public class CustomerUnitTest {
    
    @InjectMocks
    CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void givenRepeatedCPFUser_whenCreatingCustomer_thenThrowsError(){
        CustomerDTO customer = new CustomerDTO("name", "12345678910");
        doReturn(true).when(customerRepository).existsByCpf(any());

        CustomerCpfConflictException exception = assertThrows(CustomerCpfConflictException.class, () -> customerService.save(customer));

        assertNotNull(exception);
		assertEquals("This CPF already exists!", exception.getMessage());
		verify(customerRepository, times(0)).save(any());
		verify(customerRepository, times(1)).existsByCpf(any());
    }
}
