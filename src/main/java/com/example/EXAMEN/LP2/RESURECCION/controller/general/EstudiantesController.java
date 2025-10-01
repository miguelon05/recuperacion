package com.example.EXAMEN.LP2.RESURECCION.controller.general;

import com.example.EXAMEN.LP2.RESURECCION.dto.EstudiantesDTO;
import com.example.EXAMEN.LP2.RESURECCION.service.service.EstudiantesService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estudiantes")
public class EstudiantesController {
    private final EstudiantesService estudianteService;

    public EstudiantesController(EstudiantesService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public ResponseEntity<List<EstudiantesDTO>> findAll() {
        List<EstudiantesDTO> estudiantes = estudianteService.findAll();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudiantesDTO> read(@PathVariable Long id) throws ServiceException {
        EstudiantesDTO estudiante = estudianteService.findById(id);
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping
    public ResponseEntity<EstudiantesDTO> create(@RequestBody EstudiantesDTO estudianteDTO) throws ServiceException {
        EstudiantesDTO estudianteCreado = estudianteService.create(estudianteDTO);
        return new ResponseEntity<>(estudianteCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudiantesDTO> update(@PathVariable Long id, @RequestBody EstudiantesDTO estudianteDTO) throws ServiceException {
        EstudiantesDTO estudianteActualizado = estudianteService.update(id, estudianteDTO);
        return ResponseEntity.ok(estudianteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        estudianteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
