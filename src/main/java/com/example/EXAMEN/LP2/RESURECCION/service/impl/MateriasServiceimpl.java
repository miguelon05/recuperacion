package com.example.EXAMEN.LP2.RESURECCION.service.impl;
import com.example.EXAMEN.LP2.RESURECCION.controller.exceptions.ResourceNotFoundException;
import com.example.EXAMEN.LP2.RESURECCION.dto.MateriasDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Materias;
import com.example.EXAMEN.LP2.RESURECCION.mappers.MateriasMapper;
import com.example.EXAMEN.LP2.RESURECCION.repository.MateriasRepository;
import com.example.EXAMEN.LP2.RESURECCION.service.service.MateriasService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriasServiceimpl implements MateriasService {
    private final MateriasRepository materiasRepository;
    private final MateriasMapper materiaMapper;

    public MateriasServiceimpl(MateriasRepository materiasRepository, MateriasMapper materiaMapper) {
        this.materiasRepository = materiasRepository;
        this.materiaMapper = materiaMapper;
    }

    @Override
    public MateriasDTO create(MateriasDTO materiaDTO) {
        if (materiaDTO == null) {
            throw new IllegalArgumentException("La materia no puede ser nula");
        }
        Materias materia = materiaMapper.toEntity(materiaDTO);
        Materias materiaGuardada = materiasRepository.save(materia);
        return materiaMapper.toDTO(materiaGuardada);
    }

    @Override
    public MateriasDTO update(Long id, MateriasDTO materiaDTO) {
        if (id == null || materiaDTO == null) {
            throw new IllegalArgumentException("El ID y la materia no pueden ser nulos");
        }

        Materias materiaExistente = materiasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + id));

        materiaExistente.setMateria(materiaDTO.getMateria());

        Materias materiaActualizada = materiasRepository.save(materiaExistente);
        return materiaMapper.toDTO(materiaActualizada);
    }

    @Override
    public MateriasDTO findById(Long id) {
        Materias materia = materiasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + id));
        return materiaMapper.toDTO(materia);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        if (!materiasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Materia no encontrada con ID: " + id);
        }

        materiasRepository.deleteById(id);
    }

    @Override
    public List<MateriasDTO> findAll() {
        List<Materias> materias = materiasRepository.findAll();
        return materias.stream().map(materiaMapper::toDTO).toList();
    }

}
