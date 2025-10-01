package com.example.EXAMEN.LP2.RESURECCION.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstudiantesDTO {
    private Long idestudiantes;
    private String nombres;
    private String apellidos;
    private String dirreccion;
    private String telefono;
    private List<MateriasDTO> materias;
    private List<NotasDTO> notas;

}
