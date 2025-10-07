package com.example.EXAMEN.LP2.RESURECCION.mappers;


import com.example.EXAMEN.LP2.RESURECCION.dto.NotaDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Nota;
import com.example.EXAMEN.LP2.RESURECCION.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;


@Mapper(componentModel = "spring")
public interface NotaMapper extends BaseMappers<Nota, NotaDTO> {
    @Mapping(source = "estudiante.id", target = "estudianteId")
    @Mapping(source = "materia.id", target = "materiaId")
    NotaDTO toDTO(Nota nota);

    @InheritInverseConfiguration
    Nota toEntity(NotaDTO notaDTO);
}
