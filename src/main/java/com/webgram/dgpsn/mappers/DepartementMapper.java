package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.DepartementEntity;
import com.webgram.dgpsn.models.DepartementDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.DepartementExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class DepartementMapper implements EntityMapper<DepartementDto, DepartementEntity> {

   @Mapping(target = "region.id", source = "regionId")
   public abstract DepartementEntity asEntity(DepartementDto departementEntity);

      public abstract DepartementDto asDto(DepartementEntity departementEntity);
   public abstract DepartementExcelDTO asExcelDto(DepartementEntity entity);
   }
