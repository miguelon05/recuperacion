package com.example.EXAMEN.LP2.RESURECCION.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="MATERIAS")
public class Materias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MATERIA")
    private Long idmaterias;

    @Column(name = "MATERIA", nullable = false)
    private String materia;

    @ManyToOne
    @JoinColumn(name = "ID_MATERIA", nullable = false, insertable = false, updatable = false)
    private Estudiantes estudiante;

}
