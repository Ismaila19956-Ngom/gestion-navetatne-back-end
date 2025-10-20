package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.ReviewEntity;
import com.webgram.dgpsn.models.ReviewDTO;
import com.webgram.dgpsn.services.modelExcel.ReviewExcelDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ReviewMapper extends EntityMapper<ReviewDTO, ReviewEntity> {

    @Override
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    ReviewEntity asEntity(ReviewDTO dto);

    @Named("getProjet")
    default ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }

    ReviewEntity asEntity(ReviewExcelDTO dto);

    ReviewExcelDTO asExcelDto(ReviewEntity entity);
}
