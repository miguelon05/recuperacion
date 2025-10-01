package com.example.EXAMEN.LP2.RESURECCION.mappers;


import com.example.EXAMEN.LP2.RESURECCION.dto.MateriasDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Materias;
import com.example.EXAMEN.LP2.RESURECCION.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;


@Mapper(componentModel = "spring")
public interface MateriasMapper extends BaseMappers<Materias,MateriasDTO> {
    @Mapping(source = "idmaterias", target = "idmaterias")
    @Mapping(source = "materia", target = "materia")
    MateriasDTO toDTO(Materias materias);

    @InheritInverseConfiguration
    Materias toEntity(MateriasDTO materiasDTO);
}
