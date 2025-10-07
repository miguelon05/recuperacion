package com.example.EXAMEN.LP2.RESURECCION.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotaDTO {
    private Long id;
    private BigDecimal calificacion;
    private Long estudianteId;
    private Long materiaId;
}
