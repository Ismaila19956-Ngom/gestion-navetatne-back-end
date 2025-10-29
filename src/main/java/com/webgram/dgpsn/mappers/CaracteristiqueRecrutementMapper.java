package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CaracteristiqueRecrutementEntity;
import com.webgram.dgpsn.models.CaracteristiqueRecrutementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

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
    CaracteristiqueRecrutementEntity asEntity(CaracteristiqueRecrutementDTO dto);
}
