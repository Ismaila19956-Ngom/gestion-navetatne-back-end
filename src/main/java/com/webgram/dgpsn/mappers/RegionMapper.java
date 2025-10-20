package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.RegionEntity;
import com.webgram.dgpsn.models.RegionDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.RegionExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class RegionMapper implements EntityMapper<RegionDto, RegionEntity> {

      public abstract RegionEntity asEntity(RegionDto regionEntity);

      public abstract RegionDto asDto(RegionEntity regionEntity);
   public abstract RegionExcelDTO asExcelDto(RegionEntity entity);
   }
