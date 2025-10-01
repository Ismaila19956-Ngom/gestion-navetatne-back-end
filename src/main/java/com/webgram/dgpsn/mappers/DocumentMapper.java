package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.DocumentEntity;
import com.webgram.dgpsn.entities.FolderEntity;
import com.webgram.dgpsn.models.DocumentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class DocumentMapper implements EntityMapper<DocumentDto, DocumentEntity> {

   @Override
   @Mapping(target = "folder", source = "folderId",qualifiedByName ="getFolderId")
   public abstract DocumentEntity asEntity(DocumentDto dto);


//   public abstract DocumentEntity asEntity(DocumentDto documentEntity);
   public abstract DocumentDto asDto(DocumentDto documentEntity);


    @Named("getFolderId")
    public FolderEntity getFolderId(Long folderId) {
        if(Objects.nonNull(folderId)) {
            return FolderEntity.builder().id(folderId).build();
        }
        return null;
    }

}
