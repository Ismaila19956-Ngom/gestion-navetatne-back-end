package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.ActorProjetDTO;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.RoleRepository;
import com.webgram.dgpsn.services.modelExcel.ActorProjectExcelDTO;

import java.text.MessageFormat;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ActorProjetMapper implements EntityMapper<ActorProjetDTO, ActorProjetEntity> {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "agent", source = "agentId", qualifiedByName = "getAgent")
    @Mapping(target = "role", source = "roleId", qualifiedByName = "getRole")
    public abstract ActorProjetEntity asEntity(ActorProjetDTO actorProjetDTO);

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {

        if(Objects.nonNull(projetId)) {
            return ManagementUnitEntity.builder().id(projetId).build();
        }
        return null;
    }

    @Named("getAgent")
    public AgentEntity getAgent(Long agentId) {
        if(Objects.nonNull(agentId)) {
            return AgentEntity.builder().id(agentId).build();
        }
        return null;
    }

    @Named("getRole")
    public RoleEntity getRole(Long roleId) {
        if(Objects.nonNull(roleId)) {
            return RoleEntity.builder().id(roleId).build();
        }
        return null;
    }

    @Mapping(target = "agent", source = "matriculeAgent", qualifiedByName = "getAgentByMatricule")
    @Mapping(target = "role", source = "codeRole", qualifiedByName = "getRoleByCode")
    public abstract ActorProjetEntity asEntity(ActorProjectExcelDTO dto);

    @Named("getAgentByMatricule")
    public AgentEntity getAgentByMatricule(String matricule) {
        if(StringUtils.isNotEmpty(matricule)){
            var agent = agentRepository.findByMatricule(matricule)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce matricule agent n'existe pas", matricule)));
            return agent;
        }
        return null;
    }

    @Named("getRoleByCode")
    public RoleEntity getRoleByCode(String code) {
        if(StringUtils.isNotEmpty(code)){
            var role = roleRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code role n'existe pas", code)));
            return role;
        }
        return null;
    }

    @Mapping(source = "agent.matricule", target = "matriculeAgent")
    @Mapping(source = "role.code", target = "codeRole")
    public abstract ActorProjectExcelDTO asExcelDto(ActorProjetEntity entity);
}

