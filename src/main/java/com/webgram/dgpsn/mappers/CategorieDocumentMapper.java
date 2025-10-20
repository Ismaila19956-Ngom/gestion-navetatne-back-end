package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CategorieDocumentEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.models.CategorieDocumentDTO;

import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategorieDocumentMapper extends EntityMapper<CategorieDocumentDTO, CategorieDocumentEntity> {



    @Mapping(target = "typeDocument", source = "typeDocumentId",qualifiedByName = "mapDocument")
    CategorieDocumentEntity asEntity(CategorieDocumentDTO dto);


    @Mapping(source = "typeDocument.id", target = "typeDocumentId")
    CategorieDocumentDTO asDto(CategorieDocumentEntity categorieDocumentEntity);


    @Mapping(source = "typeDocument.id", target = "typeDocumentId")
    List<CategorieDocumentDTO> parse(List<CategorieDocumentEntity>entityList);

    @Mapping(target = "typeDocument", source = "typeDocumentId",qualifiedByName = "mapDocument")
    List<CategorieDocumentEntity> parseToEntity(List<CategorieDocumentDTO> entities);



    @Named("mapDocument")
    default public LabelEntity mapDocument(Long documentId ) {
        if(Objects.nonNull(documentId)) {
          return LabelEntity.builder().id(documentId).build();

        }
        return null;
    }
}
