package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalDTO {
    
    @NotNull(message = "This field is required")
    @Positive(message = "Must be a positive number")
    private Integer daysRented;

    @NotNull(message = "This field is required")
    @Positive(message = "Must be a positive number")
    private Long customerId;

    @NotNull(message = "This field is required")
    @Positive(message = "Must be a positive number")
    private Long gameId;
}
