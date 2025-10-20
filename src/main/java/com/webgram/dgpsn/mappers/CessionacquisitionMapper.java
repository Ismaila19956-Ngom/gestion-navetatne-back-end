package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CessionacquisitionEntity;
import com.webgram.dgpsn.models.CessionacquisitionDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.CessionacquisitionExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class CessionacquisitionMapper implements EntityMapper<CessionacquisitionDto, CessionacquisitionEntity> {

   @Mapping(target = "entreprise.id", source = "entrepriseId")
   public abstract CessionacquisitionEntity asEntity(CessionacquisitionDto cessionacquisitionEntity);

      public abstract CessionacquisitionDto asDto(CessionacquisitionEntity cessionacquisitionEntity);
   public abstract CessionacquisitionExcelDTO asExcelDto(CessionacquisitionEntity entity);
   }
