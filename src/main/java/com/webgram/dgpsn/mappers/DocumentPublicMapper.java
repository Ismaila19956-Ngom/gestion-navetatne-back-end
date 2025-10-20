package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.DocumentPublicDTO;

import java.util.HashSet;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DocumentPublicMapper extends EntityMapper<DocumentPublicDTO, DocumentPublicEntity> {

    @Override
    @Mapping(target = "documentType", source = "documentTypeId", qualifiedByName = "getDocumentType")
    @Mapping(target = "folder", source = "folderId", qualifiedByName = "getFolder")
    @Mapping(target = "partners", source = "partnerIds", qualifiedByName = "getPartners")
    @Mapping(target = "sectors", source = "sectorIds", qualifiedByName = "getSectors")
    DocumentPublicEntity asEntity(DocumentPublicDTO documentPublicDTO);

    @Named("getDocumentType")
    default LabelEntity getDocumentType(Long documentTypeId) {
        return (documentTypeId != null)? LabelEntity.builder().id(documentTypeId).build(): null;
    }

    @Named("getFolder")
    default FolderEntity getFolder(Long folderId) {
        return (folderId != null)? FolderEntity.builder().id(folderId).build(): null;
    }

    @Named("getPartners")
    default Set<StructureEntity> getPartners(Set<Long> partnerIds) {
        Set<StructureEntity> partners = new HashSet<>();
        if(!partnerIds.isEmpty()) {
            partnerIds.stream().forEach(partnerId -> {
                partners.add(StructureEntity.builder().id(partnerId).build());
            });
        }
        return partners;
    }

    @Named("getSectors")
    default Set<SubSectorEntity> getSectors(Set<Long> sectorIds) {
        Set<SubSectorEntity> sectors = new HashSet<>();
        if(sectors.isEmpty()) {
            sectorIds.stream().forEach(sectorId -> {
                sectors.add(SubSectorEntity.builder().id(sectorId).build());
            });
        }
        return sectors;
    }
}
