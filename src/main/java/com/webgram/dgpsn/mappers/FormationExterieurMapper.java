package com.webgram.dgpsn.mappers;


import com.webgram.dgpsn.entities.FormationExterieurEntity;
import com.webgram.dgpsn.models.FormationExterieurDTO;
import com.webgram.dgpsn.services.modelExcel.FormationExterieurExcelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FormationExterieurMapper extends EntityMapper<FormationExterieurDTO, FormationExterieurEntity> {


    FormationExterieurEntity asEntity(FormationExterieurDTO dto);


    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FormationExterieurDTO dto, @org.mapstruct.MappingTarget FormationExterieurEntity entity);




    @Mapping(target = "titreFormation", source = "titreFormation")
    @Mapping(target = "organismeFormateur", source = "organismeFormateur")
    @Mapping(target = "dateDebut", source = "dateDebut")
    @Mapping(target = "statut", source = "statut", qualifiedByName = "formatStatut")
    FormationExterieurExcelDTO asExcelDto(FormationExterieurEntity entity);


    @Named("formatStatut")
    default String formatStatut(Enum<?> value) {
        return value != null ? value.name() : "";
    }
}
