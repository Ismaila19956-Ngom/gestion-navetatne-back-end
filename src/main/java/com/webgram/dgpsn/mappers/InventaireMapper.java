package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.InventaireEntity;
import com.webgram.dgpsn.models.InventaireDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface InventaireMapper extends EntityMapper<InventaireDTO, InventaireEntity> {

    InventaireDTO asDto(InventaireEntity entity);

    InventaireEntity asEntity(InventaireDTO dto);
}