package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CaracteristiqueRecrutementEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.PromoteurEntity;
import com.webgram.dgpsn.models.CaracteristiqueRecrutementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CaracteristiqueRecrutementMapper extends EntityMapper<CaracteristiqueRecrutementDTO, CaracteristiqueRecrutementEntity> {

    // Mapping de l'entité vers le DTO
    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "libelle", target = "libelle")
    CaracteristiqueRecrutementDTO asDto(CaracteristiqueRecrutementEntity entity);

    // Mapping du DTO vers l'entité
    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "libelle", target = "libelle")
    @Mapping(source = "caracteristiqueTypeId", target = "caracteristiqueType",qualifiedByName = "getcaracteristiqueType")
    CaracteristiqueRecrutementEntity asEntity(CaracteristiqueRecrutementDTO dto);

    @Named("getcaracteristiqueType")
    default LabelEntity getcaracteristiqueType(Long id){
        if (Objects.nonNull(id)){
            return LabelEntity.builder().id(id).build();
        }
        return null;
    }
}
