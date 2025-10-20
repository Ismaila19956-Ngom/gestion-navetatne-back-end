package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.TypeagEntity;
import com.webgram.dgpsn.models.TypeagDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.TypeagExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class TypeagMapper implements EntityMapper<TypeagDto, TypeagEntity> {

      public abstract TypeagEntity asEntity(TypeagDto typeagEntity);

      public abstract TypeagDto asDto(TypeagEntity typeagEntity);
   public abstract TypeagExcelDTO asExcelDto(TypeagEntity entity);
   }
