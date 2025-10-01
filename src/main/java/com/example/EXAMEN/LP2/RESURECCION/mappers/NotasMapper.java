package com.example.EXAMEN.LP2.RESURECCION.mappers;


import com.example.EXAMEN.LP2.RESURECCION.dto.NotasDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Notas;
import com.example.EXAMEN.LP2.RESURECCION.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring", uses = { MateriasMapper.class, EstudiantesMapper.class })
public interface NotasMapper extends BaseMappers<Notas, NotasDTO> {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "estudiante.idestudiantes", target = "idestudiantes")
    @Mapping(source = "materia.idmaterias", target = "idmaterias")
    @Mapping(source = "nota", target = "nota")
    NotasDTO toDTO(Notas notas);

    @InheritInverseConfiguration
    Notas toEntity(NotasDTO notasDTO);
}
