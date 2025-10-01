package com.example.EXAMEN.LP2.RESURECCION.service.impl;
import com.example.EXAMEN.LP2.RESURECCION.controller.exceptions.ResourceNotFoundException;
import com.example.EXAMEN.LP2.RESURECCION.dto.NotasDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Notas;
import com.example.EXAMEN.LP2.RESURECCION.entity.Estudiantes;
import com.example.EXAMEN.LP2.RESURECCION.entity.Materias;
import com.example.EXAMEN.LP2.RESURECCION.mappers.NotasMapper;
import com.example.EXAMEN.LP2.RESURECCION.repository.NotasRepository;
import com.example.EXAMEN.LP2.RESURECCION.repository.EstudiantesRepository;
import com.example.EXAMEN.LP2.RESURECCION.repository.MateriasRepository;
import com.example.EXAMEN.LP2.RESURECCION.service.service.Notasservice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Notaserviceimpl implements Notasservice {
    private final NotasRepository notasRepository;
    private final NotasMapper notaMapper;
    private final EstudiantesRepository estudianteRepository;
    private final MateriasRepository materiasRepository;

    public Notaserviceimpl(NotasRepository notasRepository, NotasMapper notaMapper, EstudiantesRepository estudianteRepository, MateriasRepository materiasRepository) {
        this.notasRepository = notasRepository;
        this.notaMapper = notaMapper;
        this.estudianteRepository = estudianteRepository;
        this.materiasRepository = materiasRepository;
    }

    @Transactional
    @Override
    public NotasDTO create(NotasDTO notaDTO) {
        if (notaDTO == null) {
            throw new IllegalArgumentException("La nota no puede ser nula");
        }

        Estudiantes estudiante = estudianteRepository.findById(notaDTO.getIdestudiantes())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + notaDTO.getIdestudiantes()));

        Materias materia = materiasRepository.findById(notaDTO.getIdmaterias())
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + notaDTO.getIdmaterias()));

        Notas nota = new Notas();
        nota.setEstudiante(estudiante);
        nota.setMateria(materia);
        nota.setNota(notaDTO.getNota());

        Notas notaGuardada = notasRepository.save(nota);
        return notaMapper.toDTO(notaGuardada);
    }

    @Transactional
    @Override
    public NotasDTO update(Long id, NotasDTO notaDTO) {
        if (id == null || notaDTO == null) {
            throw new IllegalArgumentException("El ID y la nota no pueden ser nulos");
        }

        Notas notaExistente = notasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota no encontrada con ID: " + id));

        Estudiantes estudiante = estudianteRepository.findById(notaDTO.getIdestudiantes())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + notaDTO.getIdestudiantes()));

        Materias materia = materiasRepository.findById(notaDTO.getIdmaterias())
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + notaDTO.getIdmaterias()));

        notaExistente.setEstudiante(estudiante);
        notaExistente.setMateria(materia);
        notaExistente.setNota(notaDTO.getNota());

        Notas notaActualizada = notasRepository.save(notaExistente);
        return notaMapper.toDTO(notaActualizada);
    }

    @Transactional(readOnly = true)
    @Override
    public NotasDTO findById(Long id) {
        Notas nota = notasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota no encontrada con ID: " + id));
        return notaMapper.toDTO(nota);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        if (!notasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nota no encontrada con ID: " + id);
        }

        notasRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotasDTO> findAll() {
        List<Notas> notas = notasRepository.findAll();
        return notas.stream().map(notaMapper::toDTO).toList();
    }
}
