package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RecommandationMapper;
import com.webgram.dgpsn.models.RecommandationDTO;
import com.webgram.dgpsn.repositories.RecommendationRepository;
import com.webgram.dgpsn.services.RecommandationService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecommandationServiceImpl implements RecommandationService {
    private final RecommendationRepository recommendationRepository;
    private final RecommandationMapper recommandationMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ASSIGNMENT_RECOMMANDATION)
    public RecommandationDTO create(RecommandationDTO recommandationDTO) {
         var savedRecommandation = recommendationRepository.save(recommandationMapper.asEntity(recommandationDTO));

        log.info("Recommandation successfully added {}", savedRecommandation);

        return recommandationMapper.asDto(savedRecommandation);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ASSIGNMENT_RECOMMANDATION)
    public RecommandationDTO update(RecommandationDTO recommandationDTO) {
        try{
            if(recommendationRepository.existsById(recommandationDTO.getId())) {
                var recommandation = recommandationMapper.asEntity(recommandationDTO);

                var updatedRecommandation = recommandationMapper.asDto(recommendationRepository.save(recommandation));

                log.info("Recommandation successfully updated {} ", recommandation.getId());

                return updatedRecommandation;
            } else {
                throw new ResourceNotFoundException("Recommandation", recommandationDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Recommandation", recommandationDTO.getId());
        }
    }

    @Override
    public RecommandationDTO read(Long recommandationId) {
        var recommandation = recommendationRepository
                .findById(recommandationId)
                .orElseThrow(()-> new ResourceNotFoundException("Recommandation", recommandationId));

        log.info("reading recommandation id {}", recommandationId);

        return recommandationMapper.asDto(recommandation);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ASSIGNMENT_RECOMMANDATION)
    public void delete(Long recommandationId) {
        try {
            recommendationRepository.deleteById(recommandationId);
            log.info("The recommandation id {} is deleted", recommandationId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Recommandation", recommandationId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ASSIGNMENT_RECOMMANDATION)
    public Page<RecommandationDTO> readAll(
            Pageable pageable,
            String libelle,
            String responsable,
            String deadline,
            Long issueLogId,
            Long riskId,
            Long assignmentId,
            Long statusId,
            Long projectId
    ) {
        return recommendationRepository
                .readAllByFilters(pageable, libelle, responsable, deadline, issueLogId, riskId, assignmentId, statusId, projectId)
                .map(recommandationMapper::asDto);
    }
}
