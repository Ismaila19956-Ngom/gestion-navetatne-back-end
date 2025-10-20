package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.ReviewRiskEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ReviewRiskMapper;
import com.webgram.dgpsn.mappers.RiskMapper;
import com.webgram.dgpsn.models.ReviewRiskDTO;
import com.webgram.dgpsn.repositories.ReviewRepository;
import com.webgram.dgpsn.repositories.ReviewRiskRepository;
import com.webgram.dgpsn.repositories.RiskRepository;
import com.webgram.dgpsn.services.ReviewRiskService;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ReviewRiskServiceImpl implements ReviewRiskService {
    private final ReviewRepository reviewRepository;
    private final ReviewRiskRepository reviewRiskRepository;
    private final ReviewRiskMapper reviewRiskMapper;
    private final RiskRepository riskRepository;
    private final RiskMapper riskMapper;


    String REVIEW_RISK_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id reviewRisk {0}";

    @Override
    public void createAllReviewRisk(List<ReviewRiskDTO> reviewRisks) {
        var reviewRisksEntity = reviewRisks.stream().map(reviewRiskMapper::asEntity).collect(Collectors.toList());
        reviewRiskRepository.saveAll(reviewRisksEntity);
        log.trace("create list reviewRisk end ok - createdReviewRisk: {}", reviewRisksEntity);

    }

    @Override
    @Transactional
    @Journal(actionType = ActionType.ADD_REVIEW_RISK)
    public ReviewRiskDTO createReviewRisk(ReviewRiskDTO reviewRiskDTO) {
        var createdRisk = riskRepository.save(riskMapper.asEntity(reviewRiskDTO.getRisk()));
        var reviewEntity = reviewRepository.findById(reviewRiskDTO.getReviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Review id not found"));
        var reviewRiskEntity = ReviewRiskEntity
                .builder()
                .risk(createdRisk)
                .review(reviewEntity)
                .build();
        var createdReviewRisk = reviewRiskRepository.save(reviewRiskEntity);
        log.info("createdReviewRisk end ok - createdReviewRiskId: {}", createdReviewRisk.getId());
        log.trace("createdReviewRisk end ok - createdReviewRisk: {}", createdReviewRisk);
        return reviewRiskMapper.asDto(createdReviewRisk);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_REVIEW_RISK)
    public ReviewRiskDTO updateReviewRisk(ReviewRiskDTO reviewRiskDTO) {
        if(!reviewRiskRepository.existsById(reviewRiskDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(REVIEW_RISK_IDENTIFIER_NOT_FOUND_MESSAGE, reviewRiskDTO.getId()));
        }
        var updatedReviewRisk = reviewRiskRepository.save(reviewRiskMapper.asEntity(reviewRiskDTO));
        log.info("updatedReviewRisk ok id {}", updatedReviewRisk.getId());
        log.trace("updatedReviewRisk ok  {}", updatedReviewRisk);
        return reviewRiskMapper.asDto(updatedReviewRisk);
    }

    @Override
    public ReviewRiskDTO readReviewRisk(Long id) {
        var reviewRisk = reviewRiskRepository.findById(id)
                .map(reviewRiskMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(REVIEW_RISK_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read reviewRisk end ok - Id: {}", id);
        log.trace("read reviewRisk end ok - reviewRisk: {}", reviewRisk);
        return reviewRisk;
    }

    @Override
    @Journal(actionType = ActionType.DELETE_REVIEW_RISK)
    public void deleteReview(Long id) {
        if(!reviewRiskRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(REVIEW_RISK_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        reviewRiskRepository.deleteById(id);
        log.info("delete reviewRisk ok id {}", id);
    }

    @Override
    @Journal(actionType = ActionType.READ_REVIEW_RISK)
    public Page<ReviewRiskDTO> readAllReviewRisk(
            Pageable pageable, Long reviewId, Long riskId, String libelle, Date identificationDate,
            Date resolutionDate, Long criticityId, Long statusId, Long natureId
    ) {
        var reviewRisks = reviewRiskRepository
                .readAllByFilters(pageable, reviewId, riskId, libelle, identificationDate, resolutionDate, criticityId, statusId, natureId)
                .map(reviewRiskMapper::asDto);
        log.trace("list reviewRisk ok {}", reviewRisks);
        return reviewRisks;
    }
}
