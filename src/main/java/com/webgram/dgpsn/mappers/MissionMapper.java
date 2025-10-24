package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.MissionEntity;
import com.webgram.dgpsn.models.MissionDTO;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.MissionExcelDTO;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MissionMapper extends EntityMapper<MissionDTO, MissionEntity> {

    MissionEntity asEntity(MissionDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(MissionDTO dto, @MappingTarget MissionEntity entity);

    @Mapping(target = "numeroOrdre", source = "numeroOrdre")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "objet", source = "objet")
    @Mapping(target = "destination", source = "destination")
    @Mapping(target = "dateDebut", source = "dateDebut")
    @Mapping(target = "dateFin", source = "dateFin")
    @Mapping(target = "duree", source = "duree")
    @Mapping(target = "budget", source = "budget")
    @Mapping(target = "agent", source = "agent.nom")
    @Mapping(target = "statut", source = "statut", qualifiedByName = "formatStatut")
    @Mapping(target = "rapport", source = "rapport")
    @Mapping(target = "dateRapport", source = "dateRapport")
    MissionExcelDTO asExcelDto(MissionEntity entity);

    @Named("formatStatut")
    default String formatStatut(Enum<?> value) {
        return value != null ? value.name() : "";
    }
}
