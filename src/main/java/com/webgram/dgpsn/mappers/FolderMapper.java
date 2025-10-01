package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.FolderEntity;
import com.webgram.dgpsn.models.FolderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FolderMapper implements EntityMapper<FolderDto, FolderEntity> {
   @Mapping(target = "parent.id", source = "parentId")
   public abstract FolderEntity asEntity(FolderDto folderEntity);

   public FolderDto asDto(FolderEntity folderEntity) {
      return FolderDto.builder()
              .id(folderEntity.getId())
              .code(folderEntity.getCode())
              .libelle(folderEntity.getLibelle())
              .parentId(folderEntity.getParent() != null ? folderEntity.getParent().getId() : null)
              .children(folderEntity.getChildren().stream()
                      .map(this::asDto)
                      .collect(Collectors.toList()))
              .build();
   }
}
