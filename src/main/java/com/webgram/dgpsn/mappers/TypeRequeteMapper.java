package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.TypeRequeteEntity;
import com.webgram.dgpsn.models.TypeRequeteDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TypeRequeteMapper extends EntityMapper<TypeRequeteDTO, TypeRequeteEntity> {

    @Override
    @Mapping(target = "categorieRequete", source = "categorieRequeteId", qualifiedByName = "getCategorieRequete")
    TypeRequeteEntity asEntity(TypeRequeteDTO dto);

    @Named("getCategorieRequete")
    default LabelEntity getCategorieRequete(Long categorieRequeteId) {
        return LabelEntity.builder().id(categorieRequeteId).build();
    }
}
