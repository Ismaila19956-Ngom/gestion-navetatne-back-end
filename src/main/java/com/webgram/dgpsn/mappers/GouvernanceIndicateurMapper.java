package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.GouvernanceIndicateurEntity;
import com.webgram.dgpsn.entities.IndicatorEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.GouvernanceIndicateurDTO;
import com.webgram.dgpsn.repositories.GouvernanceIndicateurRepository;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class GouvernanceIndicateurMapper implements EntityMapper<GouvernanceIndicateurDTO, GouvernanceIndicateurEntity > {

    @Autowired
    private GouvernanceIndicateurRepository gouvernanceIndicateurRepository;
    @Autowired
    private LabelRepository labelRepository;


    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "indicateur", source = "indicateurId", qualifiedByName = "getIndicateur")
    @Mapping(target = "phases", source = "phaseIds", qualifiedByName = "getPhases")

    public abstract GouvernanceIndicateurEntity  asEntity(GouvernanceIndicateurDTO dto);

    @Named("getPhases")
    public Set<LabelEntity> getPhases(List<Long> phaseIds) {
        if (phaseIds == null || phaseIds.isEmpty()) {
            return Collections.emptySet();
        }
        return labelRepository.findAllByIds(phaseIds);
    }
    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }
    @Named("getIndicateur")
    public IndicatorEntity getIndicateur(Long indicateurId) {
        if (indicateurId == null) {
            return null;
        }
        return IndicatorEntity.builder().id(indicateurId).build();
    }

}
