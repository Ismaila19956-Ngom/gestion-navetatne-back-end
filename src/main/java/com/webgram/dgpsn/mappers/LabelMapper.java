package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.models.LabelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LabelMapper extends EntityMapper<LabelDTO, LabelEntity> {
    @Mapping(target = "category", source = "categoryId",qualifiedByName ="getCategory")
    LabelEntity asEntity(LabelDTO dto);

    @Named("getCategory")
    default LabelEntity getCategory(Long categoryId){
        if(Objects.nonNull(categoryId)){
            return LabelEntity.builder().id(categoryId).build();
        }
        return  null;
    }

}
