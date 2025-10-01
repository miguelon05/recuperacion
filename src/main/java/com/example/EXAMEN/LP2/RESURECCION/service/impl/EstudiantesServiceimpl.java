package com.example.EXAMEN.LP2.RESURECCION.service.impl;

import com.example.EXAMEN.LP2.RESURECCION.controller.exceptions.ResourceNotFoundException;
import com.example.EXAMEN.LP2.RESURECCION.controller.exceptions.ResourceValidationException;
import com.example.EXAMEN.LP2.RESURECCION.dto.EstudiantesDTO;
import com.example.EXAMEN.LP2.RESURECCION.dto.MateriasDTO;
import com.example.EXAMEN.LP2.RESURECCION.dto.NotasDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Estudiantes;
import com.example.EXAMEN.LP2.RESURECCION.entity.Materias;
import com.example.EXAMEN.LP2.RESURECCION.entity.Notas;
import com.example.EXAMEN.LP2.RESURECCION.mappers.EstudiantesMapper;
import com.example.EXAMEN.LP2.RESURECCION.repository.EstudiantesRepository;
import com.example.EXAMEN.LP2.RESURECCION.repository.MateriasRepository;
import com.example.EXAMEN.LP2.RESURECCION.repository.NotasRepository;
import com.example.EXAMEN.LP2.RESURECCION.service.service.EstudiantesService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class EstudiantesServiceimpl implements EstudiantesService {

    private final EstudiantesRepository estudiantesRepository;
    private final EstudiantesMapper estudiantesMapper;
    private final MateriasRepository materiasRepository;
    private final NotasRepository notasRepository;

    public EstudiantesServiceimpl(EstudiantesRepository estudianteRepository,
                                 EstudiantesMapper estudianteMapper,
                                 MateriasRepository materiasRepository,
                                 NotasRepository notasRepository) {
        this.estudiantesRepository = estudianteRepository;
        this.estudiantesMapper = estudianteMapper;
        this.materiasRepository = materiasRepository;
        this.notasRepository = notasRepository;
    }

    @Transactional
    @Override
    public EstudiantesDTO create(EstudiantesDTO estudianteDTO) throws ServiceException {
        try {
            // Crear el estudiante y guardarlo
            Estudiantes estudiante = estudiantesMapper.toEntity(estudianteDTO);
            Estudiantes estudianteGuardado = estudiantesRepository.save(estudiante);

            // Verificar y asociar las materias al estudiante
            List<Materias> materias = new ArrayList<>();
            for (MateriasDTO materiaDTO : estudianteDTO.getMaterias()) {
                Materias materia = materiasRepository.findById(materiaDTO.getIdmaterias())
                        .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + materiaDTO.getIdmaterias()));
                materias.add(materia);
            }
            estudianteGuardado.setMaterias(materias); // Asignar las materias al estudiante

            // Guardar las notas asociadas al estudiante
            List<Notas> notas = new ArrayList<>();
            for (NotasDTO notaDTO : estudianteDTO.getNotas()) {
                Materias materia = materiasRepository.findById(notaDTO.getIdmaterias())
                        .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con ID: " + notaDTO.getIdmaterias()));

                // Crear la entidad Nota y asociarla al estudiante y materia
                Notas nota = new Notas();
                nota.setEstudiante(estudianteGuardado);
                nota.setMateria(materia);
                nota.setNota(notaDTO.getNota());

                notas.add(nota);
            }

            // Guardar las notas en la base de datos
            notasRepository.saveAll(notas);

            // Retornar el DTO del estudiante guardado
            return estudiantesMapper.toDTO(estudianteGuardado);
        } catch (Exception e) {
            throw new ServiceException("Error al crear el estudiante", e);
        }
    }

    @Transactional
    @Override
    public EstudiantesDTO update(Long id, EstudiantesDTO estudianteDTO) throws ServiceException {
        try {
            Estudiantes estudianteExistente = estudiantesRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));

            estudianteExistente.setNombres(estudianteDTO.getNombres());
            estudianteExistente.setApellidos(estudianteDTO.getApellidos());
            estudianteExistente.setDireccion(estudianteDTO.getDirreccion());
            estudianteExistente.setTelefono(estudianteDTO.getTelefono());

            return estudiantesMapper.toDTO(estudiantesRepository.save(estudianteExistente));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar el estudiante", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public EstudiantesDTO findById(Long id) throws ServiceException {
        try {
            Estudiantes estudiante = estudiantesRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));
            return estudiantesMapper.toDTO(estudiante);
        } catch (Exception e) {
            throw new ServiceException("Error al obtener el estudiante", e);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws ServiceException {
        try {
            Estudiantes estudianteExistente = estudiantesRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));

            if (!estudianteExistente.getNotas().isEmpty()) {
                notasRepository.deleteAll(estudianteExistente.getNotas());
            }

            estudiantesRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar el estudiante", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<EstudiantesDTO> findAll() throws ServiceException {
        try {
            List<Estudiantes> estudiantes = estudiantesRepository.findAll();
            return estudiantes.stream()
                    .map(estudiantesMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            throw new ServiceException("Error al listar estudiantes", e);
        }
    }


}
