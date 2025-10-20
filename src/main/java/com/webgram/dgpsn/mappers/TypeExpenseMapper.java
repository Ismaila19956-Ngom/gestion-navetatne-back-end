package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.TypeExpenseEntity;
import com.webgram.dgpsn.models.TypeExpenseDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TypeExpenseMapper extends EntityMapper<TypeExpenseDTO, TypeExpenseEntity> {

    @Override
    @Mapping(target = "categorieDepense", source = "categorieDepenseId", qualifiedByName = "getCategorieDepense")
    TypeExpenseEntity asEntity(TypeExpenseDTO dto);

    @Named("getCategorieDepense")
    default LabelEntity getCategorieDepense(Long categorieDepenseId) {
        return LabelEntity.builder().id(categorieDepenseId).build();
    }
}
