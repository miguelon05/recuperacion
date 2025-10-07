package com.example.EXAMEN.LP2.RESURECCION.mappers;

import com.example.EXAMEN.LP2.RESURECCION.dto.EstudianteDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Estudiante;
import com.example.EXAMEN.LP2.RESURECCION.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring", uses = {NotaMapper.class})
public interface EstudianteMapper extends BaseMappers<Estudiante, EstudianteDTO>{
    @Mapping(source = "notas", target = "notas")
    EstudianteDTO toDTO(Estudiante estudiante);

    @InheritInverseConfiguration
    Estudiante toEntity(EstudianteDTO estudianteDTO);
}
