package com.banquito.branch.controller.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchHolidayDto {
    
    private String idHoliday;
    
    @NotBlank(message = "El ID de la sucursal es requerido")
    private String idBranch;
    
    @NotNull(message = "La fecha es requerida")
    @FutureOrPresent(message = "La fecha debe ser presente o futura")
    private LocalDate date;
    
    @NotBlank(message = "El nombre del feriado es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;
}
