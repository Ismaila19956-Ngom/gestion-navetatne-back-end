package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.SuiviInventaireEntity;
import com.webgram.dgpsn.models.SuiviInventaireDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SuiviInventaireMapper extends EntityMapper<SuiviInventaireDTO, SuiviInventaireEntity> {

    @Mapping(source = "typeInventaire.id", target = "typeInventaireId")
    @Mapping(source = "inventaire.id", target = "inventaireId")
    @Mapping(source = "bailleur.id", target = "bailleurId")
    @Mapping(source = "etat.id", target = "etatId")
    @Mapping(source = "localisation.id", target = "localisationId")
    SuiviInventaireDTO asDto(SuiviInventaireEntity entity);

    @Mapping(source = "typeInventaireId", target = "typeInventaire.id")
    @Mapping(source = "inventaireId", target = "inventaire.id")
    @Mapping(source = "bailleurId", target = "bailleur.id")
    @Mapping(source = "etatId", target = "etat.id")
    @Mapping(source = "localisationId", target = "localisation.id")
    SuiviInventaireEntity asEntity(SuiviInventaireDTO dto);
}