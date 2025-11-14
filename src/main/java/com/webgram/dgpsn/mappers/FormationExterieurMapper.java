package com.webgram.dgpsn.mappers;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.FormationExterieurEntity;
import com.webgram.dgpsn.entities.ParticipantEntity;
import com.webgram.dgpsn.models.FormationExterieurDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import java.util.Objects;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FormationExterieurMapper extends EntityMapper<FormationExterieurDTO, FormationExterieurEntity> {

    @Mapping(target = "agent", source = "agentId", qualifiedByName = "getAgent")
    FormationExterieurEntity asEntity(FormationExterieurDTO dto);

   @Named("getAgent")
    default AgentEntity getAgent(Long agentId) {
        if (Objects.nonNull(agentId)) {
            return AgentEntity.builder().id(agentId).build();
        }
        return null;
    }


    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FormationExterieurDTO dto, @org.mapstruct.MappingTarget FormationExterieurEntity entity);
}
