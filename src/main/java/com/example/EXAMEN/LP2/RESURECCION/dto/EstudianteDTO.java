package com.example.EXAMEN.LP2.RESURECCION.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstudianteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private List<NotaDTO> notas;

}
