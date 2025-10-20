package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.MembreEntity;
import com.webgram.dgpsn.models.MembreDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.MembreExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class MembreMapper implements EntityMapper<MembreDto, MembreEntity> {

   @Mapping(target = "conseiladministratif.id", source = "conseiladministratifId")
   public abstract MembreEntity asEntity(MembreDto membreEntity);

      public abstract MembreDto asDto(MembreEntity membreEntity);
   public abstract MembreExcelDTO asExcelDto(MembreEntity entity);
   }
