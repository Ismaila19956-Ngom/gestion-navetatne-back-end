package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.EntrepriseEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.MediathequeEntity;
import com.webgram.dgpsn.models.MediathequeDTO;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MediathequeMapper extends EntityMapper<MediathequeDTO, MediathequeEntity> {

    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "entreprise", source = "entrepriseId", qualifiedByName = "getEntreprise")
    MediathequeEntity asEntity(MediathequeDTO dto);


    @Named("getProjet")
    default ManagementUnitEntity getProjet(Long projetId) {
        if (Objects.nonNull(projetId)) {
            return ManagementUnitEntity.builder().id(projetId).build();
        }
        return null;
    }
    @Named("getEntreprise")
    default EntrepriseEntity getEntreprise(Long entrepriseId) {
        if(Objects.nonNull(entrepriseId)) {
            return EntrepriseEntity.builder().id(entrepriseId).build();
        }
        return null;

    }
}
