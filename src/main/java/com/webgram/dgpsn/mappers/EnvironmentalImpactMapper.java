package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.EnvironmentalImpactEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.EnvironmentalImpactDTO;
import com.webgram.dgpsn.repositories.EnvironmentalImpactRepository;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.List;
import java.util.Set;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class EnvironmentalImpactMapper implements EntityMapper<EnvironmentalImpactDTO, EnvironmentalImpactEntity> {

    @Autowired
    private EnvironmentalImpactRepository environmentalImpactRepository;
    @Autowired
    private LabelRepository labelRepository;


    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "categorie", source = "categorieId", qualifiedByName = "getCategorie")
    @Mapping(target = "phases", source = "phaseIds", qualifiedByName = "getPhases")

    public abstract EnvironmentalImpactEntity asEntity(EnvironmentalImpactDTO dto);

    @Named("getPhases")
    public Set<LabelEntity> getRisks(List<Long> phaseIds) {
        return labelRepository.findAllByIds(phaseIds);
    }
    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }
    @Named("getCategorie")
    public LabelEntity getCategorie(Long categorieId) {
        return LabelEntity.builder().id(categorieId).build();
    }

}
