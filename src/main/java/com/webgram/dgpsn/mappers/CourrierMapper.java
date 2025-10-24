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
    @Mapping(source = "type", target = "type")
    @Mapping(source = "nature", target = "nature")
    CourrierDTO toDTO(CourrierEntity entity);

    // Conversion DTO → Entity
    @Mapping(source = "type", target = "type")
    @Mapping(source = "nature", target = "nature")
    CourrierEntity toEntity(CourrierDTO dto);
}