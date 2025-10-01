package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.DirectionEntity;
import com.webgram.dgpsn.entities.enums.SexType;
import com.webgram.dgpsn.entities.enums.SituationMatrimoniale;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.AgentDTO;
import com.webgram.dgpsn.repositories.DirectionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class AgentMapper implements EntityMapper<AgentDTO, AgentEntity> {
    @Autowired
    private DirectionRepository directionRepository;
    @Override
    @Mapping(target = "direction.id", source = "directionId")
    public abstract AgentEntity asEntity(AgentDTO agentDTO);

//    @Mapping(target = "sexe", source = "sexe", qualifiedByName = "getSexeByCode")
//    @Mapping(target = "situationMatrimoniale", source = "situationMatrimoniale", qualifiedByName = "getSituationMatrimonialeByCode")
//    @Mapping(target = "direction", source = "direction", qualifiedByName = "getDirectionByCode")
//    public abstract AgentEntity asEntity(AgentExcelDTO dto);

//    @Mapping(target = "sexe", source = "sexe.description")
//    @Mapping(target = "situationMatrimoniale", source = "situationMatrimoniale.description")
//    @Mapping(target = "direction", source = "direction.libelle")
//    public abstract AgentExcelDTO asExcelDto(AgentEntity entity);

    @Named("getSexeByCode")
    public SexType getSexeByCode(String code) {
        return SexType.valueOf(code);
    }

    @Named("getSituationMatrimonialeByCode")
    public SituationMatrimoniale getSituationMatrimonialeByCode(String code) {
        return SituationMatrimoniale.valueOf(code);
    }

    @Named("getDirectionByCode")
    public DirectionEntity getDirectionByCode(String code) {
        return directionRepository
                .findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Une direction avec ce code '%s' n'existe pas !", code)));
    }
}
