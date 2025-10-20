package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.SubCategoryEntity;
import com.webgram.dgpsn.models.SubCategoryDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SubCategoryMapper extends EntityMapper<SubCategoryDTO, SubCategoryEntity> {

    @Mapping(target = "category.id", source = "categoryId")
    SubCategoryEntity asEntity(SubCategoryDTO dto);
}
