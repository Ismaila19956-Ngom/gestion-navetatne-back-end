package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CategoriebudgetaireEntity;
import com.webgram.dgpsn.models.CategoriebudgetaireDto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.CategoriebudgetaireExcelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
   public abstract class CategoriebudgetaireMapper implements EntityMapper<CategoriebudgetaireDto, CategoriebudgetaireEntity> {

      public abstract CategoriebudgetaireEntity asEntity(CategoriebudgetaireDto categoriebudgetaireEntity);

      public abstract CategoriebudgetaireDto asDto(CategoriebudgetaireEntity categoriebudgetaireEntity);
   public abstract CategoriebudgetaireExcelDTO asExcelDto(CategoriebudgetaireEntity entity);
   }
