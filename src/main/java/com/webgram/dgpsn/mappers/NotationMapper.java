package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.NotationEntity;
import com.webgram.dgpsn.models.NotationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotationMapper {

    @Mapping(source = "candidat.id", target = "candidatId")
    NotationDTO toDto(NotationEntity entity);

    @Mapping(source = "candidatId", target = "candidat.id")
    NotationEntity toEntity(NotationDTO dto);

    // MapStruct gère automatiquement la conversion élément par élément
    List<NotationDTO> toDtoList(List<NotationEntity> entities);

    List<NotationEntity> toEntityList(List<NotationDTO> dtos);
}
