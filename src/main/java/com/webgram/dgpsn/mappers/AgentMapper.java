package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.DirectionEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.AgentDTO;
import com.webgram.dgpsn.repositories.StructureRepository;
import com.webgram.dgpsn.services.modelExcel.AgentExcelDTO;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class AgentMapper implements EntityMapper<AgentDTO, AgentEntity> {

    @Autowired
    private StructureRepository structureRepository;

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


//    @Mapping(target = "fonction", source = "codeFonction", qualifiedByName = "getFonctionByCode")
//    @Mapping(target = "structure", source = "codeStructure", qualifiedByName = "getStructureByCode")
    public abstract AgentEntity asEntity(AgentExcelDTO dto);


    @Named("getStructureByCode")
    public StructureEntity getStructureByCode(String code) {
        return structureRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("code structure {} n'existe pas", code)));
    }

//    @Mapping(source = "fonction.code", target = "codeFonction")
//    @Mapping(source = "structure.code", target = "codeStructure")
    @Mapping(target = "dateNaissance", source = "dateNaissance", qualifiedByName = "formatDate")
    @Mapping(target = "sexe", source = "sexe", qualifiedByName = "formatSexe")

public abstract AgentExcelDTO asExcelDto(AgentEntity entity);

    @Named("formatDate")
    String formatDate(java.util.Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @Named("formatSexe")
    String formatSexe(Enum<?> sexe) {
        return sexe != null ? sexe.name() : "";
    }


}
