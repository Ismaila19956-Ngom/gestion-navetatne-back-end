package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.SocialImpactEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.SocialImpactDTO;
import com.webgram.dgpsn.repositories.SocialImpactRepository;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.List;
import java.util.Set;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class SocialImpactMapper implements EntityMapper<SocialImpactDTO, SocialImpactEntity> {

    @Autowired
    private SocialImpactRepository socialImpactRepository;
    @Autowired
    private LabelRepository labelRepository;


    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "categorie", source = "categorieId", qualifiedByName = "getCategorie")
    @Mapping(target = "typeImpact", source = "typeImpactId", qualifiedByName = "getTypeImpact")
    @Mapping(target = "phases", source = "phaseIds", qualifiedByName = "getPhases")

    public abstract SocialImpactEntity asEntity(SocialImpactDTO dto);

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
    @Named("getTypeImpact")
    public LabelEntity getTypeImpact(Long typeImpactId) {
        return LabelEntity.builder().id(typeImpactId).build();
    }

}
