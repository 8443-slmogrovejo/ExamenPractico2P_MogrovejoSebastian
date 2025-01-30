package com.banquito.branch.controller.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    
    private String id;
    
    @NotBlank(message = "El email es requerido")
    @Email(message = "El formato del email no es válido")
    private String emailAddress;
    
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;
    
    @NotBlank(message = "El número de teléfono es requerido")
    @Pattern(regexp = "^[0-9]{10}$", message = "El número de teléfono debe tener 10 dígitos numéricos")
    private String phoneNumber;
    
    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "^(ACT|INA)$", message = "El estado debe ser ACT o INA")
    private String state;
    
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
}
