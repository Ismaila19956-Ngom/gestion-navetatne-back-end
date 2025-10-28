package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.AtelierEntity;
import com.webgram.dgpsn.entities.FormationExterieurEntity;
import com.webgram.dgpsn.entities.ParticipantEntity;
import com.webgram.dgpsn.models.ParticipantDTO;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)


public interface ParticipantMapper extends EntityMapper<ParticipantDTO, ParticipantEntity> {



    @Mapping(target = "agent", source = "agentId", qualifiedByName = "getAgent")
    @Mapping(target = "atelier", source = "atelierId", qualifiedByName = "getAtelier")
    @Mapping(target = "formation", source = "formationId", qualifiedByName = "getFormation")

    ParticipantEntity asEntity(ParticipantDTO dto);
    @Named("getAgent")
    default AgentEntity getAgent(Long agentId) {
        if (Objects.nonNull(agentId)) {
            return AgentEntity.builder().id(agentId).build();
        }
        return null;
    }

    @Named("getFormation")
    default FormationExterieurEntity getFormation(Long agentId) {
        if (Objects.nonNull(agentId)) {
            return FormationExterieurEntity.builder().id(agentId).build();
        }
        return null;
    }


    @Named("getAtelier")
    default AtelierEntity getAtelier(Long agentId) {
        if (Objects.nonNull(agentId)) {
            return AtelierEntity.builder().id(agentId).build();
        }
        return null;
    }



    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ParticipantDTO dto, @org.mapstruct.MappingTarget ParticipantEntity entity);



}