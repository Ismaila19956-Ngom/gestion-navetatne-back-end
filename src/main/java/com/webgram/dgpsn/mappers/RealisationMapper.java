package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.FournisseurEntity;
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
    @Mapping(target = "fournisseur", source = "fournisseurId", qualifiedByName = "getFournisseur")
    public abstract RealisationEntity asEntity(RealisationDTO dto);

    @Override
    @Mapping(source = "fournisseur", target = "fournisseur")
    @Mapping(source = "fournisseur.id", target = "fournisseurId")
    @Mapping(source = "ligneBudgetaire.id", target = "ligneBudgetaireId")
    @Mapping(source = "realisations.id", target = "realisationsId")
    public abstract RealisationDTO asDto(RealisationEntity entity);

    @Named("getLigneBudgetaire")
    public LigneBudgetaireEntity getLigneBudgetaire(Long ligneBudgetaireId) {
        return LigneBudgetaireEntity.builder().id(ligneBudgetaireId).build();
    }

    @Named("getRealisations")
    public PlanComptableElementEntity getRealisations(Long realisationsId) {
        return PlanComptableElementEntity.builder().id(realisationsId).build();
    }
    @Named("getFournisseur")
    public FournisseurEntity getFournisseur(Long fournisseurId) {
        if (fournisseurId == null) {
            return null;
        }
        return FournisseurEntity.builder().id(fournisseurId).build();
    }
}