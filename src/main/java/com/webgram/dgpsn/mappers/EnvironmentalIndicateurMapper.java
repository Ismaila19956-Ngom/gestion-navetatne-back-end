package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.EnvironmentalIndicateurEntity ;
import com.webgram.dgpsn.entities.IndicatorEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.EnvironmentalIndicateurDTO;
import com.webgram.dgpsn.repositories.EnvironmentalIndicateurRepository;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.List;
import java.util.Set;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class EnvironmentalIndicateurMapper implements EntityMapper<EnvironmentalIndicateurDTO, EnvironmentalIndicateurEntity > {

    @Autowired
    private EnvironmentalIndicateurRepository environmentalIndicateurRepository;
    @Autowired
    private LabelRepository labelRepository;


    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "indicateur", source = "indicateurId", qualifiedByName = "getIndicateur")
    @Mapping(target = "phases", source = "phaseIds", qualifiedByName = "getPhases")

    public abstract EnvironmentalIndicateurEntity  asEntity(EnvironmentalIndicateurDTO dto);

    @Named("getPhases")
    public Set<LabelEntity> getRisks(List<Long> phaseIds) {
        return labelRepository.findAllByIds(phaseIds);
    }
    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }
    @Named("getIndicateur")
    public IndicatorEntity getIndicateur(Long indicateurId) {
        return IndicatorEntity.builder().id(indicateurId).build();
    }

}
