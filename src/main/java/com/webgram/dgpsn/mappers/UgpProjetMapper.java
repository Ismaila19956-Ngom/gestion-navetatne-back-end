package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.UgpProjetDTO;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.RoleRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class UgpProjetMapper implements EntityMapper<UgpProjetDTO, UgpProjetEntity> {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "ugpRole", source = "ugpRoleId", qualifiedByName = "getUgpRole")
    public abstract UgpProjetEntity asEntity(UgpProjetDTO ugpProjetDTO);

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }

    @Named("getUgpRole")
    public RoleEntity getUgpRole(Long roleId) {
        return RoleEntity.builder().id(roleId).build();
    }

//    @Mapping(target = "ugpRole", source = "codeRole", qualifiedByName = "getRoleByCode")
//    public abstract UgpProjetEntity asEntity(ActorProjectExcelDTO dto);
//
//    @Named("getRoleByCode")
//    public RoleEntity getRoleByCode(String code) {
//        if(StringUtils.isNotEmpty(code)){
//            var role = roleRepository.findByCode(code)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code role n'existe pas", code)));
//            return role;
//        }
//        return null;
//    }

//    @Mapping(source = "role.code", target = "codeRole")
//    public abstract ActorProjectExcelDTO asExcelDto(ActorProjetEntity entity);
}

