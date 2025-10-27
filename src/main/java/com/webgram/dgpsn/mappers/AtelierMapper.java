package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.AtelierEntity;
import com.webgram.dgpsn.models.AtelierDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AtelierMapper extends EntityMapper<AtelierDTO, AtelierEntity> {



    @Mapping(target = "agent", source = "agentId", qualifiedByName = "getAgent")
    AtelierEntity asEntity(AtelierDTO dto);

    @Named("getAgent")
    default AgentEntity getAgent(Long agentId) {
        if (Objects.nonNull(agentId)) {
            return AgentEntity.builder().id(agentId).build();
        }
        return null;
    }
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AtelierDTO dto, @org.mapstruct.MappingTarget AtelierEntity entity);



}