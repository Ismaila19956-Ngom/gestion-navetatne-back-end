package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.GouvernanceImpactEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.GouvernanceImpactDTO;
import com.webgram.dgpsn.repositories.GouvernanceImpactRepository;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class GouvernanceImpactMapper implements EntityMapper<GouvernanceImpactDTO, GouvernanceImpactEntity> {

    @Autowired
    private GouvernanceImpactRepository gouvernanceImpactRepository;
    @Autowired
    private LabelRepository labelRepository;


    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "categorie", source = "categorieId", qualifiedByName = "getCategorie")
    @Mapping(target = "phases", source = "phaseIds", qualifiedByName = "getPhases")
    @Mapping(target = "partiePrenantes", source = "partiePrenantesIds", qualifiedByName = "getPartiePrenantes")

    public abstract GouvernanceImpactEntity asEntity(GouvernanceImpactDTO dto);

    @Named("getPartiePrenantes")
    public Set<LabelEntity> getPartiePrenantes(List<Long> partiePrenantesIds) {
        if (partiePrenantesIds == null || partiePrenantesIds.isEmpty()) {
            return null;
        }
        return labelRepository.findAllByIds(partiePrenantesIds);
    }

    @Named("getPhases")
    public Set<LabelEntity> getPhases(List<Long> phaseIds) {
        if (phaseIds == null || phaseIds.isEmpty()) {
            return Collections.emptySet();
        }
        return labelRepository.findAllByIds(phaseIds);
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        if (projetId == null) {
            return null;
        }
        return ManagementUnitEntity.builder().id(projetId).build();
    }

    @Named("getCategorie")
    public LabelEntity getCategorie(Long categorieId) {
        if (categorieId == null) {
            return null;
        }
        return LabelEntity.builder().id(categorieId).build();
    }


}
