package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.TypereunionEntity;
import com.webgram.dgpsn.models.TypereunionDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.TypereunionExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class TypereunionMapper implements EntityMapper<TypereunionDto, TypereunionEntity> {

      public abstract TypereunionEntity asEntity(TypereunionDto typereunionEntity);

      public abstract TypereunionDto asDto(TypereunionEntity typereunionEntity);
   public abstract TypereunionExcelDTO asExcelDto(TypereunionEntity entity);
   }
