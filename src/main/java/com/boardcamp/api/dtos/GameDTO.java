package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO {

    @NotBlank(message = "This field is required")
    private String name;

    @NotBlank(message = "This field is required")
    private String image;

    @NotNull(message = "This field is required")
    @Positive(message = "Must be a positive number")
    private Integer stockTotal;

    @NotNull(message = "This field is required")
    @Positive(message = "Must be a positive number")
    private Integer pricePerDay;
}
