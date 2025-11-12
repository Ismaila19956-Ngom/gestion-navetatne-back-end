package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.GestionContratEntity;
import com.webgram.dgpsn.models.GestionContratDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GestionContratMapper extends EntityMapper<GestionContratDTO, GestionContratEntity> {
    @Mapping(source = "typeContratId", target = "typeContrat.id")
    @Override
    GestionContratEntity asEntity(GestionContratDTO dto);

    @Override
    GestionContratDTO asDto(GestionContratEntity entity);
}
