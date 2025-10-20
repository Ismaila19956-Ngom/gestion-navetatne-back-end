package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.QueryEntity;
import com.webgram.dgpsn.entities.StatusQueryEntity;
import com.webgram.dgpsn.models.StatusQueryDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StatusQueryMapper extends EntityMapper<StatusQueryDTO, StatusQueryEntity> {

    @Override
    @Mapping(target = "query", source = "queryId", qualifiedByName = "getQuery")
    StatusQueryEntity asEntity(StatusQueryDTO statusDTO);

    @Named("getQuery")
    default QueryEntity getQuery(Long queryId) {
            var queryEntity = QueryEntity.builder().id(queryId).build();
            return queryEntity;
    }


}
