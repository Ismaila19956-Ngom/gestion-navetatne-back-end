package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.AgentBafEntity;
import com.webgram.dgpsn.models.AgentBafDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AgentBafMapper extends EntityMapper<AgentBafDTO, AgentBafEntity> {

    @Mapping(source = "poste.id", target = "posteId")
    @Mapping(source = "fonction.id", target = "fonctionId")
    @Mapping(source = "profil.id", target = "profilId")
    @Mapping(source = "typeContrat.id", target = "typeContratId")
    @Mapping(source = "diplome.id", target = "diplomeId")
    AgentBafDTO asDto(AgentBafEntity entity);

    @Mapping(source = "posteId", target = "poste.id")
    @Mapping(source = "typeContratId", target = "typeContrat.id")
    @Mapping(source = "fonctionId", target = "fonction.id")
    @Mapping(source = "profilId", target = "profil.id")
    @Mapping(source = "diplomeId", target = "diplome.id")
    AgentBafEntity asEntity(AgentBafDTO dto);
}