package com.example.EXAMEN.LP2.RESURECCION.mappers;

import com.example.EXAMEN.LP2.RESURECCION.dto.EstudiantesDTO;
import com.example.EXAMEN.LP2.RESURECCION.entity.Estudiantes;
import com.example.EXAMEN.LP2.RESURECCION.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring", uses = { MateriasMapper.class, NotasMapper.class })
public interface EstudiantesMapper extends BaseMappers<Estudiantes, EstudiantesDTO>{

    @Mapping(source = "idestudiantes", target = "idestudiantes")
    @Mapping(source = "nombres", target = "nombres")
    @Mapping(source = "apellidos", target = "apellidos")
    @Mapping(source = "direccion", target = "dirreccion")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "notas", target = "notas")
    @Mapping(source = "materias", target = "materias")
    EstudiantesDTO toDTO(Estudiantes estudiantes);

    @InheritInverseConfiguration
    Estudiantes toEntity(EstudiantesDTO estudiantesDTO);
}
