package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.PassationEntity;
import com.webgram.dgpsn.models.PassationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PassationMapper extends EntityMapper<PassationDTO, PassationEntity> {

    @Mapping(source = "sourceFinancement.id", target = "sourceFinancementId")
    @Mapping(source = "typeMarche.id", target = "typeMarcheId")
    @Mapping(source = "modePassation.id", target = "modePassationId")
    PassationDTO asDto(PassationEntity entity);

    @Mapping(source = "sourceFinancementId", target = "sourceFinancement.id")
    @Mapping(source = "typeMarcheId", target = "typeMarche.id")
    @Mapping(source = "modePassationId", target = "modePassation.id")
    PassationEntity asEntity(PassationDTO dto);
}