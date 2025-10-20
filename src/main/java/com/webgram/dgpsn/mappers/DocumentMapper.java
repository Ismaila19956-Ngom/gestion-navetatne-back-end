package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.DocumentDto;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDto, DocumentEntity> {

    @Override
    @Mapping(target = "documentType", source = "documentTypeId", qualifiedByName = "getDocumentType")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "entreprise", source = "entrepriseId", qualifiedByName = "getEntreprise")
    DocumentEntity asEntity(DocumentDto dto);

    @Named("getDocumentType")
    default LabelEntity getDocumentType(Long documentTypeId) {
        if(Objects.nonNull(documentTypeId)) {
            return LabelEntity.builder().id(documentTypeId).build();
        }
        return null;
    }

    @Named("getProjet")
    default ManagementUnitEntity getProjet(Long projetId) {
        if(Objects.nonNull(projetId)) {
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
