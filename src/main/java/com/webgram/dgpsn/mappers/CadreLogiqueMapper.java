package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CadreLogiqueEntity;
import com.webgram.dgpsn.models.CadreLogiqueDTO;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CadreLogiqueMapper extends EntityMapper<CadreLogiqueDTO, CadreLogiqueEntity> {
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "getParent")
    CadreLogiqueEntity asEntity(CadreLogiqueDTO dto);

    @Named("getParent")
    default CadreLogiqueEntity getParent(Long parentId) {
       if(Objects.nonNull(parentId)) {
           return CadreLogiqueEntity.builder().id(parentId).build();
       }
       return null;
    }
}
