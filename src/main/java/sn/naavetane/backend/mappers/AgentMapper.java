package sn.naavetane.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sn.naavetane.backend.entities.AgentEntity;
import sn.naavetane.backend.entities.DirectionEntity;
import sn.naavetane.backend.entities.LabelEntity;
import sn.naavetane.backend.entities.StructureEntity;
import sn.naavetane.backend.models.AgentDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class AgentMapper implements EntityMapper<AgentDTO, AgentEntity> {

    @Override
    @Mapping(target = "fonction", source = "fonctionId", qualifiedByName = "getFonction")
    @Mapping(target = "structure", source = "structureId", qualifiedByName = "getStructure")
    @Mapping(target = "direction", source = "directionId", qualifiedByName = "getDirection")
    public abstract AgentEntity asEntity(AgentDTO agentDTO);

    @Named("getFonction")
    public LabelEntity getFonction(Long fonctionId) {
        if(Objects.nonNull(fonctionId)) {
            return LabelEntity.builder().id(fonctionId).build();
        }
        return null;
    }

    @Named("getStructure")
    public StructureEntity getStructure(Long structureId) {
        if(Objects.nonNull(structureId)) {
            return StructureEntity.builder().id(structureId).build();
        }
        return null;
    }

    @Named("getDirection")
    public DirectionEntity getDirection(Long directionId) {
        if(Objects.nonNull(directionId)) {
            return DirectionEntity.builder().id(directionId).build();
        }
        return null;
    }
}
