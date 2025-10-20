package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.FormejuridiqueEntity;
import com.webgram.dgpsn.models.FormejuridiqueDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.FormejuridiqueExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class FormejuridiqueMapper implements EntityMapper<FormejuridiqueDto, FormejuridiqueEntity> {

      public abstract FormejuridiqueEntity asEntity(FormejuridiqueDto formejuridiqueEntity);

      public abstract FormejuridiqueDto asDto(FormejuridiqueEntity formejuridiqueEntity);
   public abstract FormejuridiqueExcelDTO asExcelDto(FormejuridiqueEntity entity);
   }
