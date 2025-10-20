package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.ProjetEntrepriseDTO;
import com.webgram.dgpsn.repositories.ActorProjetRepository;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ProjetEntrepriseMapper implements EntityMapper<ProjetEntrepriseDTO, ProjetEntrepriseEntity> {

    @Autowired
    private ActorProjetRepository actorProjetRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Override
    @Mapping(target = "projet", source = "projectId", qualifiedByName = "getProjet")
    @Mapping(target = "entreprise", source = "entrepriseId", qualifiedByName = "getEntreprise")
    @Mapping(target = "roleEntreprise", source = "roleEntrepriseId", qualifiedByName = "getRoleEntreprise")
    @Mapping(target = "flag", source = "flagId", qualifiedByName = "getFlag")
    public abstract ProjetEntrepriseEntity asEntity(ProjetEntrepriseDTO projetEntrepriseDTO);

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {

        if(Objects.nonNull(projetId)) {
            return ManagementUnitEntity.builder().id(projetId).build();
        }
        return null;
    }

    @Named("getEntreprise")
    public EntrepriseEntity getEntreprise(Long entrepriseId) {
        if(Objects.nonNull(entrepriseId)) {
            return EntrepriseEntity.builder().id(entrepriseId).build();
        }
        return null;
    }

    @Named("getRoleEntreprise")
    public LabelEntity getRoleEntreprise(Long roleId) {
        if(Objects.nonNull(roleId)) {
            return LabelEntity.builder().id(roleId).build();
        }
        return null;
    }

    @Named("getFlag")
    public LabelEntity getFlag(Long flagId) {
        if(Objects.nonNull(flagId)) {
            return LabelEntity.builder().id(flagId).build();
        }
        return null;
    }

}

