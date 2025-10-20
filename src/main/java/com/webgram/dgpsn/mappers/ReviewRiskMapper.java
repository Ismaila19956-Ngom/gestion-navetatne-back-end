package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ReviewEntity;
import com.webgram.dgpsn.entities.ReviewRiskEntity;
import com.webgram.dgpsn.entities.RiskEntity;
import com.webgram.dgpsn.models.ReviewRiskDTO;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ReviewRiskMapper extends EntityMapper<ReviewRiskDTO, ReviewRiskEntity> {

    @Override
    @Mapping(target = "review", source = "reviewId", qualifiedByName = "getReview")
    @Mapping(target = "risk", source = "riskId", qualifiedByName = "getRisk")
    ReviewRiskEntity asEntity(ReviewRiskDTO dto);

    @Named("getReview")
    default ReviewEntity getReview(Long reviewId) {
        return ReviewEntity.builder().id(reviewId).build();
    }

    @Named("getRisk")
    default RiskEntity getRisk(Long riskId) {
        if(Objects.nonNull(riskId)){
            return RiskEntity.builder().id(riskId).build();
        }
       return null;
    }
}
