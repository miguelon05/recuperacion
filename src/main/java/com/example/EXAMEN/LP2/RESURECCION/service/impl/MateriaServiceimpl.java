package com.example.EXAMEN.LP2.RESURECCION.service.impl;
import com.example.EXAMEN.LP2.RESURECCION.controller.exceptions.ResourceNotFoundException;
import com.example.EXAMEN.LP2.RESURECCION.dto.MateriaDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Materia;
import com.example.EXAMEN.LP2.RESURECCION.mappers.MateriaMapper;
import com.example.EXAMEN.LP2.RESURECCION.repository.MateriaRepository;
import com.example.EXAMEN.LP2.RESURECCION.service.service.MateriaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceimpl implements MateriaService {
    private final MateriaRepository materiaRepository;
    private final MateriaMapper materiaMapper;

    public MateriaServiceimpl(MateriaRepository materiaRepository, MateriaMapper materiaMapper) {
        this.materiaRepository = materiaRepository;
        this.materiaMapper = materiaMapper;
    }


    @Override
    public MateriaDTO create(MateriaDTO materiaDTO) throws ServiceException {
        try {
            Materia materia = materiaMapper.toEntity(materiaDTO);
            Materia materiaSaved = materiaRepository.save(materia);
            return materiaMapper.toDTO(materiaSaved);
        }catch(Exception e) {
            throw new ServiceException("Error al crear Materia: "+e.getMessage());
        }
    }

    @Override
    public MateriaDTO update(Long aLong, MateriaDTO materiaDTO) throws ServiceException {
        try {
            Materia materia1 = materiaRepository.findById(aLong).orElseThrow(() -> new ServiceException("No se encontró el materia1 con ID: "));

            materia1.setNombre(materiaDTO.getNombre());
            return materiaMapper.toDTO(materiaRepository.save(materia1));
        } catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar la materia: "+e);
        }
    }

    @Override
    public MateriaDTO findById(Long aLong) throws ServiceException {
        try {
            Materia materia1 =  materiaRepository.findById(aLong).orElseThrow(() -> new ServiceException("No existe la materia"));
            return materiaMapper.toDTO(materia1);
        }catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al leer la materia con id " + aLong, e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws ServiceException {
        try {
            if(!materiaRepository.findById(aLong).isPresent()){
                throw new ServiceException("No existe la materia");
            }
            materiaRepository.deleteById(aLong);
        }catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar la materia con id " + aLong, e);
        }
    }

    @Override
    public List<MateriaDTO> findAll() throws ServiceException {
        try {
            return materiaMapper.toDTOs(materiaRepository.findAll());
        }catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al listar las materias " + e);
        }
    }

}
