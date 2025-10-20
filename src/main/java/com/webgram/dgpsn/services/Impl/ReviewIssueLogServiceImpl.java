package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.ReviewIssueLogEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.IssueLogMapper;
import com.webgram.dgpsn.mappers.ReviewIssueLogMapper;
import com.webgram.dgpsn.mappers.ReviewMapper;
import com.webgram.dgpsn.models.requests.ReviewIssueLogDTO;
import com.webgram.dgpsn.models.requests.ReviewIssueLogListDTO;
import com.webgram.dgpsn.repositories.IssueLogRepository;
import com.webgram.dgpsn.repositories.ReviewIssueLogRepository;
import com.webgram.dgpsn.repositories.ReviewRepository;
import com.webgram.dgpsn.services.ReviewIssueLogService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewIssueLogServiceImpl implements ReviewIssueLogService {
    private final ReviewIssueLogRepository reviewIssueLogRepository;
    private final ReviewIssueLogMapper reviewIssueLogMapper;
    private final IssueLogRepository issueLogRepository;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final IssueLogMapper issueLogMapper;

    @Override
    public void create(ReviewIssueLogListDTO reviewIssueLogListDTO) {
        var issueLogs = reviewIssueLogListDTO.getIssueLogs();
        var review = reviewMapper.asEntity(reviewIssueLogListDTO.getReview());
        review.setId(reviewIssueLogListDTO.getReview().getId());
        List<ReviewIssueLogEntity> reviewIssueLogEntities = new ArrayList<>();

        for(Long id : issueLogs){
            var issueLog = issueLogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("issueLog not found by id", id));
            var reviewIssue = ReviewIssueLogEntity.builder().review(review).issueLog(issueLog).build();
            reviewIssueLogEntities.add(reviewIssue);
        }

        reviewIssueLogRepository.saveAll(reviewIssueLogEntities);

        log.info("ReviewIssueLog successfully added");
    }

    @Override
    @Journal(actionType = ActionType.ADD_REVIEW_ISSUE_LOG)
    public void create(ReviewIssueLogDTO reviewIssueLogDTO) {
        var issueLog = issueLogMapper.asEntity(reviewIssueLogDTO.getIssueLog());
        var issueLogSaved = issueLogRepository.save(issueLog);
        var review = reviewRepository.findById(reviewIssueLogDTO.getRevewId()).orElseThrow(()->new RuntimeException("enregistrement echoue"));
        var reviewIssue = ReviewIssueLogEntity.builder().review(review).issueLog(issueLogSaved).build();

        reviewIssueLogRepository.save(reviewIssue);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_REVIEW_RISK)
    public void delete(Long reviewIssuelogId) {
        try {
            reviewIssueLogRepository.deleteById(reviewIssuelogId);
            log.info("The reviewIssuelog id {} is deleted", reviewIssuelogId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("ReviewIssuelog", reviewIssuelogId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_REVIEW_ISSUE_LOG)
    public Page<ReviewIssueLogDTO> readAll(
            Pageable pageable,Long reviewId, Long issueLogId, String libelle,
            Date identificationDate,Date resolutionDate, Long criticityId, Long statusId, Long natureId) {
        return reviewIssueLogRepository
                .readAllByFilters(pageable, reviewId, issueLogId, libelle, identificationDate, resolutionDate, criticityId, statusId, natureId)
                .map(reviewIssueLogMapper::asDto);
    }
}
