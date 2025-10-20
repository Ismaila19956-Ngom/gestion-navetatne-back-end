package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.NaturedepenseEntity;
import com.webgram.dgpsn.models.NaturedepenseDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.NaturedepenseExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class NaturedepenseMapper implements EntityMapper<NaturedepenseDto, NaturedepenseEntity> {

      public abstract NaturedepenseEntity asEntity(NaturedepenseDto naturedepenseEntity);

      public abstract NaturedepenseDto asDto(NaturedepenseEntity naturedepenseEntity);
   public abstract NaturedepenseExcelDTO asExcelDto(NaturedepenseEntity entity);
   }
