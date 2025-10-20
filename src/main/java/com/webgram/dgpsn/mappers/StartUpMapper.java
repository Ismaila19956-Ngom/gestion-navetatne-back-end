package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.StartUpDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class StartUpMapper implements EntityMapper<StartUpDTO, StartUpEntity> {

//    @Mapping(target = "nomCompagnie", source = "nomCompagnieId", qualifiedByName = "getNomCompagnie")
//    public abstract StartUpEntity asEntity(StartUpDTO startUpDTO);
//
//
//    @Named("getNomCompagnie")
//    public LabelEntity getNomCompagnie(Long id) {
//        if(Objects.nonNull(id)) {
//            return  LabelEntity.builder().id(id).build();
//        }
//        return null;
//    }
}
