package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.BudgetPassationEntity;
import com.webgram.dgpsn.models.BudgetPassationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BudgetPassationMapper extends EntityMapper<BudgetPassationDTO, BudgetPassationEntity> {

    BudgetPassationDTO asDto(BudgetPassationEntity entity);

    BudgetPassationEntity asEntity(BudgetPassationDTO dto);
}