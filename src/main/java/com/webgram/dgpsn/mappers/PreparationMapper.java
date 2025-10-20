package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.PreparationDTO;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.LabelRepository;
import com.webgram.dgpsn.services.modelExcel.PreparationExcelDTO;

import java.text.MessageFormat;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class PreparationMapper implements EntityMapper<PreparationDTO, PreparationEntity> {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Override
    @Mapping(target = "phase", source = "phaseId", qualifiedByName = "getPhase")
    @Mapping(target = "agent", source = "agentId", qualifiedByName = "getAgent")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    public abstract PreparationEntity asEntity(PreparationDTO preparationDTO);

    @Mapping(target = "phase", source = "codePhase", qualifiedByName = "getPhaseByCode")
    @Mapping(target = "agent", source = "matriculeAgent", qualifiedByName = "getAgentByMatricule")
    public abstract PreparationEntity asEntity(PreparationExcelDTO dto);

    @Named("getPhase")
    public LabelEntity getPhase(Long phaseId) {
        if (Objects.nonNull(phaseId)){
            return LabelEntity.builder().id(phaseId).build();
        }
        else {
            return null;
        }

    }

    @Named("getAgent")
    public AgentEntity getAgent(Long agentId) {
        if (Objects.nonNull(agentId)){
            return AgentEntity.builder().id(agentId).build();

        }
        else {
            return null;
        }
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }

    @Named("getPhaseByCode")
    public LabelEntity getPhaseByCode(String code) {
        if(StringUtils.isNotEmpty(code)){
            return labelRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code phase n'existe pas", code)));
        }
        return null;
    }

    @Named("getAgentByMatricule")
    public AgentEntity getAgentByMatricule(String matricule) {
        if(StringUtils.isNotEmpty(matricule)){
            var agent = agentRepository.findByMatricule(matricule)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code statut n'existe pas", matricule)));
            return agent;
        }
        return null;
    }

    @Mapping(source = "phase.code", target = "codePhase")
    @Mapping(source = "agent.matricule", target = "matriculeAgent")
    public abstract PreparationExcelDTO asExcelDto(PreparationEntity entity);
}
