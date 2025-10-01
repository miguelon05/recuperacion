package com.example.EXAMEN.LP2.RESURECCION.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotasDTO {
    private Long id;
    private Long idestudiantes;
    private Long idmaterias;
    private BigDecimal nota;
}
