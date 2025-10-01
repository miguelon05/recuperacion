package com.example.EXAMEN.LP2.RESURECCION.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="ESTUDIANTES")
public class Estudiantes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTUDIANTE")
    private Long idestudiantes;
    @Column(name = "NOMBRES", unique = true, nullable = false, length = 45)
    private String nombres;
    @Column(name = "APELLIDOS", unique = true, nullable = false, length = 45)
    private String apellidos;
    @Column(name = "DIRECCION", unique = true, nullable = false, length = 45)
    private String direccion;
    @Column(name = "TELEFONO", unique = true, nullable = false, length = 45)
    private String telefono;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "estudiante")
    private List<Materias> materias;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "estudiante")
    private List<Notas> notas;
}
