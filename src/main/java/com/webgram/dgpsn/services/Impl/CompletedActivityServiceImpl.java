package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CompletedActivityMapper;
import com.webgram.dgpsn.models.CompletedActivityDTO;
import com.webgram.dgpsn.repositories.CompletedActivityRepository;
import com.webgram.dgpsn.services.CompletedActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompletedActivityServiceImpl implements CompletedActivityService {
    private final CompletedActivityRepository completedActivityRepository;

    private final CompletedActivityMapper completedActivityMapper;

    @Override
    public CompletedActivityDTO create(CompletedActivityDTO completedActivityDTO) {
        var savedCompletedActivity = completedActivityRepository.save(completedActivityMapper.asEntity(completedActivityDTO));
        log.info("completedActivity successfully added {}", savedCompletedActivity);
        return completedActivityMapper.asDto(savedCompletedActivity);
    }

    @Override
    public CompletedActivityDTO update(CompletedActivityDTO completedActivityDTO) {
        var completedActivity = completedActivityMapper.asEntity(completedActivityDTO);

        var updatedCompletedActivity = completedActivityMapper.asDto(completedActivityRepository.save(completedActivity));

        log.info("completedActivity successfully updated {} ", updatedCompletedActivity.getId());

        return updatedCompletedActivity;
    }

    @Override
    public CompletedActivityDTO read(Long completedActivityId) {
        var completedActivity = completedActivityRepository
                .findById(completedActivityId)
                .orElseThrow(()-> new ResourceNotFoundException("CompletedActivityDTO", completedActivityId));

        log.info("reading completedActivity id {}", completedActivity);

        return completedActivityMapper.asDto(completedActivity);
    }

    @Override
    public void delete(Long completedActivityId) {
        try {
            completedActivityRepository.deleteById(completedActivityId);
            log.info("The completedActivity id {} is deleted", completedActivityId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<CompletedActivityDTO> readAll(Pageable pageable, String code, String libelle, Date dateDebut, Date dateFin, Long issueLogId) {
        return completedActivityRepository
                .readAllByFiltering(pageable, code, libelle, dateDebut, dateFin, issueLogId)
                .map(completedActivityMapper::asDto);
    }
}
