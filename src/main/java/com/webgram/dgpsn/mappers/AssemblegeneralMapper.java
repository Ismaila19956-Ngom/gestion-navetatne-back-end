package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.AssemblegeneralEntity;
import com.webgram.dgpsn.models.AssemblegeneralDto;
import com.webgram.dgpsn.entities.ConseiladministratifEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.AssemblegeneralExcelDTO;
import com.webgram.dgpsn.entities.TypeagEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

   @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class AssemblegeneralMapper implements EntityMapper<AssemblegeneralDto, AssemblegeneralEntity> {

   @Mapping(target = "entreprise.id", source = "entrepriseId")
@Mapping(target = "conseiladministratif", source = "conseiladministratifId", qualifiedByName = "mapConseiladministratif")
@Mapping(target = "typeag", source = "typeagId", qualifiedByName = "mapTypeag")
   public abstract AssemblegeneralEntity asEntity(AssemblegeneralDto assemblegeneralEntity);

   @Named("mapConseiladministratif")
   public ConseiladministratifEntity mapConseiladministratif(Long conseiladministratifId) {
       if (Objects.nonNull(conseiladministratifId)) {
           return ConseiladministratifEntity.builder().id(conseiladministratifId).build();
       }
       return null;
    }
@Named("mapTypeag")
   public TypeagEntity mapTypeag(Long typeagId) {
       if (Objects.nonNull(typeagId)) {
           return TypeagEntity.builder().id(typeagId).build();
       }
       return null;
    }
   public abstract AssemblegeneralDto asDto(AssemblegeneralEntity assemblegeneralEntity);
   public abstract AssemblegeneralExcelDTO asExcelDto(AssemblegeneralEntity entity);
   }
