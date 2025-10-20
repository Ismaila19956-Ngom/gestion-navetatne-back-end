package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.IssueLogEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.IssueLogActionRealizedMapper;
import com.webgram.dgpsn.models.IssueLogActionRealizedDTO;
import com.webgram.dgpsn.repositories.IssueLogActionRealizedRepository;
import com.webgram.dgpsn.services.IssueLogActionRealizedService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class IssueLogActionRealizedServiceImpl implements IssueLogActionRealizedService {
    private final IssueLogActionRealizedRepository issueLogActionRealizedRepository;

    private final IssueLogActionRealizedMapper issueLogActionRealizedMapper;

    @Override
    public IssueLogActionRealizedDTO create(IssueLogActionRealizedDTO issueLogActionRealizedDTO) {
        var savedIssueLogActionRealized= issueLogActionRealizedRepository.save(issueLogActionRealizedMapper.asEntity(issueLogActionRealizedDTO));
        log.info("IssueLogActionRealized successfully added {}", savedIssueLogActionRealized);
        return issueLogActionRealizedMapper.asDto(savedIssueLogActionRealized);
    }

    @Override
    public IssueLogActionRealizedDTO update(IssueLogActionRealizedDTO issueLogActionRealizedDTO) {
        var issueLogActionRealized = issueLogActionRealizedMapper.asEntity(issueLogActionRealizedDTO);

        var updatedIssueLogActionRealized = issueLogActionRealizedMapper.asDto(issueLogActionRealizedRepository.save(issueLogActionRealized));

        log.info("IssueLogActionRealized successfully updated {} ", updatedIssueLogActionRealized.getId());

        return updatedIssueLogActionRealized;
    }

    @Override
    public IssueLogActionRealizedDTO read(Long issueLogActionRealizedId) {
        var issueLogActionRealized = issueLogActionRealizedRepository
                .findById(issueLogActionRealizedId)
                .orElseThrow(()-> new ResourceNotFoundException("IssueLogActionRealized", issueLogActionRealizedId));

        log.info("reading IssueLogActionRealized id {}", issueLogActionRealizedId);

        return issueLogActionRealizedMapper.asDto(issueLogActionRealized);
    }

    @Override
    public List<IssueLogActionRealizedDTO> readByIssueLog(Long issueLogId) {
        var issueLog = IssueLogEntity.builder().id(issueLogId).build();

        var issueLogActionRealized = issueLogActionRealizedRepository
                .findAllByIssueLog(issueLog)
                .stream().collect(Collectors.toList());

        return issueLogActionRealizedMapper.parse(issueLogActionRealized);
    }

    @Override
    public void delete(Long issueLogActionRealizedId) {
        try {
            issueLogActionRealizedRepository.deleteById(issueLogActionRealizedId);
            log.info("The IssueLogActionRealizedDTO id {} is deleted", issueLogActionRealizedId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }
}
