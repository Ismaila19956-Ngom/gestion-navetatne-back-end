package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.PassationMarketCritereDTO;

import java.util.List;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PassationMarketCritereMapper extends EntityMapper<PassationMarketCritereDTO, PassationMarketCritereEntity> {


    @Mapping(target = "passationMarketEntity", source = "passationMarketId",qualifiedByName ="getPassationMarket")
    @Mapping(target = "evaluationCriteria", source = "evaluationCriteriaId",qualifiedByName ="getEvaluationCritere")
    PassationMarketCritereEntity asEntity(PassationMarketCritereDTO dto);


    @Mapping(source = "passationMarketEntity.id", target = "passationMarketId")
    @Mapping(source = "evaluationCriteria.id", target = "evaluationCriteriaId")
    PassationMarketCritereDTO asDto( PassationMarketCritereEntity passationMarketCritere);



    @Mapping(source = "passationMarketEntity.id", target = "passationMarketId")
    @Mapping(source = "evaluationCriteria.id", target = "evaluationCriteriaId")
    List<PassationMarketCritereDTO> parse(List<PassationMarketCritereEntity>entityList);

    @Mapping(target = "passationMarketEntity", source = "passationMarketId",qualifiedByName ="getPassationMarket")
    @Mapping(target = "evaluationCriteria", source = "evaluationCriteriaId",qualifiedByName ="getEvaluationCritere")
    List<PassationMarketCritereEntity> parseToEntity(List<PassationMarketCritereDTO> entities);

    @Named("getPassationMarket")
    default PassationMarketEntity builtPassationMarket(Long passationMarketId) {
        if(Objects.nonNull(passationMarketId)){
            return PassationMarketEntity.builder().id(passationMarketId).build();
        }
        return null;
    }
    @Named("getEvaluationCritere")
    default LabelEntity getCriteriaEvaluation(Long evaluationCriteriaId) {
        if(Objects.nonNull(evaluationCriteriaId)){
            return LabelEntity.builder().id(evaluationCriteriaId).build();
        }
        return null;
    }

}
