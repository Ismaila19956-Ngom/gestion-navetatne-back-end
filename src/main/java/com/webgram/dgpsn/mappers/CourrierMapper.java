package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CourrierEntity;
import com.webgram.dgpsn.models.CourrierDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourrierMapper {

    CourrierMapper INSTANCE = Mappers.getMapper(CourrierMapper.class);

    // Conversion Entity → DTO
    @Mapping(source = "statut.id", target = "statutId")
    @Mapping(source = "nature.id", target = "natureId")
    @Mapping(source = "urgence.id", target = "urgenceId")
    CourrierDTO asDto(CourrierEntity entity);

    // Conversion DTO → Entity

    CourrierEntity asEntity(CourrierDTO dto);
}