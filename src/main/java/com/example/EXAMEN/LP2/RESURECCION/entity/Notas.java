package com.example.EXAMEN.LP2.RESURECCION.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="NOTAS")
public class Notas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTAS")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ESTUDIANTE", nullable = false)
    private Estudiantes estudiante;

    @ManyToOne
    @JoinColumn(name = "ID_MATERIA", nullable = false)
    private Materias materia;

    @Column(name = "NOTA", nullable = false)
    private BigDecimal nota;
}
