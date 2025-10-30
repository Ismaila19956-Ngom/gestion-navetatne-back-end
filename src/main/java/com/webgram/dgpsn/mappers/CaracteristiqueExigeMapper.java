package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CaracteristiqueExigeEntity;
import com.webgram.dgpsn.entities.CaracteristiqueRecrutementEntity;
import com.webgram.dgpsn.models.CaracteristiqueExigeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CaracteristiqueExigeMapper extends EntityMapper<CaracteristiqueExigeDTO, CaracteristiqueExigeEntity> {

    // Conversion Entity -> DTO
    @Mapping(target = "caracteristiqueId", source = "caracteristique.id")
    @Mapping(target = "caracteristiqueLibelle", source = "caracteristique.libelle")
    CaracteristiqueExigeDTO asDto(CaracteristiqueExigeEntity entity);

    // Conversion DTO -> Entity
    @Mapping(target = "caracteristique", source = "caracteristiqueId")
    CaracteristiqueExigeEntity asEntity(CaracteristiqueExigeDTO dto);

    // Méthode utilitaire pour recréer une entité minimale à partir d’un ID
    default CaracteristiqueRecrutementEntity map(Long value) {
        if (value == null) {
            return null;
        }
        CaracteristiqueRecrutementEntity caracteristique = new CaracteristiqueRecrutementEntity();
        caracteristique.setId(value);
        return caracteristique;
    }
}
