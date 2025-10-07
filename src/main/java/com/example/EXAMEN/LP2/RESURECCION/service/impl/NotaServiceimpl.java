package com.example.EXAMEN.LP2.RESURECCION.service.impl;
import com.example.EXAMEN.LP2.RESURECCION.controller.exceptions.ResourceNotFoundException;
import com.example.EXAMEN.LP2.RESURECCION.dto.NotaDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Nota;
import com.example.EXAMEN.LP2.RESURECCION.entity.Estudiante;
import com.example.EXAMEN.LP2.RESURECCION.entity.Materia;
import com.example.EXAMEN.LP2.RESURECCION.mappers.NotaMapper;
import com.example.EXAMEN.LP2.RESURECCION.repository.NotaRepository;
import com.example.EXAMEN.LP2.RESURECCION.repository.EstudianteRepository;
import com.example.EXAMEN.LP2.RESURECCION.repository.MateriaRepository;
import com.example.EXAMEN.LP2.RESURECCION.service.service.NotaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotaServiceimpl implements NotaService {
    private final NotaRepository notaRepository;
    private final NotaMapper notaMapper;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public NotaServiceimpl(NotaRepository notaRepository, NotaMapper notaMapper, EstudianteRepository estudianteRepository, MateriaRepository materiaRepository) {
        this.notaRepository = notaRepository;
        this.notaMapper = notaMapper;
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public NotaDTO create(NotaDTO notaDTO) throws ServiceException {
        if (notaDTO == null) {
            throw new IllegalArgumentException("La nota no puede ser nulo.");
        }

        Estudiante estudiante = estudianteRepository.findById(notaDTO.getEstudianteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estudiante no encontrado con ID: " + notaDTO.getEstudianteId()));

        Materia materia = materiaRepository.findById(notaDTO.getMateriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Materia no encontrado con ID: " + notaDTO.getMateriaId()));

        try {
            Nota nota = notaMapper.toEntity(notaDTO);

            nota.setEstudiante(estudiante);
            nota.setMateria(materia);

            return notaMapper.toDTO(notaRepository.save(nota));
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la nota", e);
        }
    }

    @Override
    public NotaDTO update(Long aLong, NotaDTO notaDTO) throws ServiceException {
        if (aLong == null || notaDTO == null) {
            throw new IllegalArgumentException("El ID y la nota no pueden ser nulos");
        }

        Nota nota = new Nota();
        nota.setCalificacion(notaDTO.getCalificacion());

        Nota existente = notaRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Notas no encontrada con ID: " + aLong));

        Estudiante estudiante = estudianteRepository.findById(notaDTO.getEstudianteId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + notaDTO.getEstudianteId()));

        Materia materia = materiaRepository.findById(notaDTO.getMateriaId()).orElseThrow(() -> new ResourceNotFoundException(
                "Materia no encontrado con ID: " + notaDTO.getMateriaId()));


        try {
            existente.setEstudiante(estudiante);
            existente.setMateria(materia);

            Nota actualizado = notaRepository.save(existente);
            return notaMapper.toDTO(actualizado);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NotaDTO findById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        Nota existente = notaRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Nota no encontrado con ID: " + aLong));
        try {
            return notaMapper.toDTO(existente);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        Nota existente = notaRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Nota no encontrado con ID: " + aLong));
        try{
            notaRepository.delete(existente);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<NotaDTO> findAll() throws ServiceException {
        List<Nota> notas = notaRepository.findAll();
        if (notas.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron notas registrados");
        }

        return notas.stream()
                .map(notaMapper::toDTO)
                .toList();
    }
}
