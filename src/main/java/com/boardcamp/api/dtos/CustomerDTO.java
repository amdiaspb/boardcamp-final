package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    @NotBlank(message = "This field is required")
    private String name;

    @NotBlank(message = "This field is required")
    @Size(min = 11, max = 11, message = "Must be exactly 11 characters long")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Must be a numeric value")
    private String cpf;
}
