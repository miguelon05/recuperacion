package com.example.EXAMEN.LP2.RESURECCION.service.impl;

import com.example.EXAMEN.LP2.RESURECCION.controller.exceptions.ResourceNotFoundException;
import com.example.EXAMEN.LP2.RESURECCION.controller.exceptions.ResourceValidationException;
import com.example.EXAMEN.LP2.RESURECCION.dto.EstudianteDTO;
import com.example.EXAMEN.LP2.RESURECCION.dto.NotaDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Estudiante;
import com.example.EXAMEN.LP2.RESURECCION.entity.Materia;
import com.example.EXAMEN.LP2.RESURECCION.entity.Nota;
import com.example.EXAMEN.LP2.RESURECCION.mappers.EstudianteMapper;
import com.example.EXAMEN.LP2.RESURECCION.repository.EstudianteRepository;
import com.example.EXAMEN.LP2.RESURECCION.repository.MateriaRepository;
import com.example.EXAMEN.LP2.RESURECCION.repository.NotaRepository;
import com.example.EXAMEN.LP2.RESURECCION.service.service.EstudianteService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteServiceimpl implements EstudianteService {


    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper estudianteMapper;
    private final NotaRepository notaRepository;
    private final MateriaRepository materiaRepository;

    public EstudianteServiceimpl(EstudianteRepository estudianteRepository, EstudianteMapper estudianteMapper, NotaRepository notaRepository, MateriaRepository materiaRepository) {
        this.estudianteRepository = estudianteRepository;
        this.estudianteMapper = estudianteMapper;
        this.notaRepository = notaRepository;
        this.materiaRepository = materiaRepository;
    }

    @Transactional
    @Override
    public EstudianteDTO create(EstudianteDTO estudianteDTO) throws ServiceException {
        if( estudianteDTO == null ) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo.");
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(estudianteDTO.getNombre());
        estudiante.setApellido(estudianteDTO.getApellido());
        estudiante.setDireccion(estudianteDTO.getDireccion());
        estudiante.setTelefono(estudianteDTO.getTelefono());


        List<Nota> notas = new ArrayList<>();
        for (NotaDTO p : estudianteDTO.getNotas()) {
            if (p.getMateriaId() == null) {
                throw new ResourceValidationException("El estudiante debe tener 1 materia");
            }
            Materia materia = materiaRepository.findById(p.getMateriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrado con ID: " + p.getMateriaId()));

            Nota nota = new Nota();
            nota.setEstudiante(estudiante);
            nota.setMateria(materia);
            nota.setCalificacion(p.getCalificacion());

            notas.add(nota);
        }
        estudiante.setNotas(notas);
        Estudiante guardado = estudianteRepository.save(estudiante);
        return estudianteMapper.toDTO(guardado);
    }

    @Transactional
    @Override
    public EstudianteDTO update(Long aLong, EstudianteDTO estudianteDTO) throws ServiceException {
        try {
            Estudiante estudiante1 = estudianteRepository.findById(aLong).orElseThrow(() -> new ServiceException("No existe el estudiante"));
            estudiante1.setNombre(estudianteDTO.getNombre());
            estudiante1.setApellido(estudianteDTO.getApellido());
            estudiante1.setDireccion(estudianteDTO.getDireccion());
            estudiante1.setTelefono(estudianteDTO.getTelefono());
            return estudianteMapper.toDTO(estudianteRepository.save(estudiante1));
        } catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar el estudiante: "+e);
        }
    }

    @Transactional
    @Override
    public EstudianteDTO findById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        Estudiante estu = estudianteRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrada con ID: " + aLong));

        return estudianteMapper.toDTO(estu);
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        if (!estudianteRepository.existsById(aLong)) {
            throw new ResourceNotFoundException("Estudiante no encontrada con ID: " + aLong);
        }

        try {
            estudianteRepository.deleteById(aLong);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("No se puede eliminar el estudiante porque tiene materias asociados", ex);
        }
    }

    @Transactional
    @Override
    public List<EstudianteDTO> findAll() throws ServiceException {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        return estudiantes.stream()
                .map(estudianteMapper::toDTO)
                .toList();
    }
}
