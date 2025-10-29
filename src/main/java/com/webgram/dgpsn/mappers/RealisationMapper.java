package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.LigneBudgetaireEntity;
import com.webgram.dgpsn.entities.RealisationEntity;
import com.webgram.dgpsn.models.RealisationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class RealisationMapper implements EntityMapper<RealisationDTO, RealisationEntity> {


    @Override
    @Mapping(target = "ligneBudgetaire", source = "ligneBudgetaireId", qualifiedByName = "getLigneBudgetaire")
    @Mapping(target = "realisations", source = "realisationsId", qualifiedByName = "getRealisations")
    public abstract RealisationEntity asEntity(RealisationDTO dto);

    @Named("getLigneBudgetaire")
    public LigneBudgetaireEntity getLigneBudgetaire(Long ligneBudgetaireId) {
        return LigneBudgetaireEntity.builder().id(ligneBudgetaireId).build();
    }

    @Named("getRealisations")
    public PlanComptableElementEntity getRealisations(Long realisationsId) {
        return PlanComptableElementEntity.builder().id(realisationsId).build();
    }
}