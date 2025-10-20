package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.DepenseEntity;
import com.webgram.dgpsn.models.DepenseDto;
import com.webgram.dgpsn.entities.NaturedepenseEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.DepenseExcelDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

   @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class DepenseMapper implements EntityMapper<DepenseDto, DepenseEntity> {

   @Mapping(target = "entreprise.id", source = "entrepriseId")
@Mapping(target = "naturedepense", source = "naturedepenseId", qualifiedByName = "mapNaturedepense")
   public abstract DepenseEntity asEntity(DepenseDto depenseEntity);

   @Named("mapNaturedepense")
   public NaturedepenseEntity mapNaturedepense(Long natureDepenseId) {
       if (Objects.nonNull(natureDepenseId)) {
           return NaturedepenseEntity.builder().id(natureDepenseId).build();
       }
       return null;
    }
   public abstract DepenseDto asDto(DepenseEntity depenseEntity);
   public abstract DepenseExcelDTO asExcelDto(DepenseEntity entity);
   }
