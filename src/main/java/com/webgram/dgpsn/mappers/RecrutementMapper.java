package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.BudgetPassationEntity;
import com.webgram.dgpsn.entities.RecrutementEntity;
import com.webgram.dgpsn.models.BudgetPassationDTO;
import com.webgram.dgpsn.models.RecrutementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RecrutementMapper extends EntityMapper<RecrutementDTO, RecrutementEntity> {

    RecrutementDTO asDto(RecrutementEntity entity);

    RecrutementEntity asEntity(RecrutementDTO dto);
}