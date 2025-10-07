package com.example.EXAMEN.LP2.RESURECCION.mappers;


import com.example.EXAMEN.LP2.RESURECCION.dto.MateriaDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Materia;
import com.example.EXAMEN.LP2.RESURECCION.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;


@Mapper(componentModel = "spring")
public interface MateriaMapper extends BaseMappers<Materia, MateriaDTO> {
}
