package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.ReviewRiskDTO;

import java.util.Date;
import java.util.List;


public interface ReviewRiskService {
    void createAllReviewRisk(List<ReviewRiskDTO> reviewRisks);
    ReviewRiskDTO createReviewRisk(ReviewRiskDTO reviewRiskDTO);
    ReviewRiskDTO updateReviewRisk(ReviewRiskDTO reviewRiskDTO);
    ReviewRiskDTO readReviewRisk(Long id);
    void deleteReview(Long id);
    Page<ReviewRiskDTO> readAllReviewRisk(
            Pageable pageable, Long reviewId, Long riskId, String libelle, Date identificationDate,
            Date resolutionDate, Long criticityId, Long statusId, Long natureId
    );

}
