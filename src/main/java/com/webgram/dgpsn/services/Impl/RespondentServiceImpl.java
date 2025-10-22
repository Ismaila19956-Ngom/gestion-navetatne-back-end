package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RespondentMapper;
import com.webgram.dgpsn.models.RespondentDTO;
import com.webgram.dgpsn.repositories.RespondentRepository;
import com.webgram.dgpsn.services.RespondentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RespondentServiceImpl implements RespondentService {
    private final RespondentRepository respondentRepository;
    private final RespondentMapper respondentMapper;

    String RESPONDENT_IDENTIFIER_NOT_FOUND_MESSAGE = "In valid id respondent {0}";

    @Override
    public RespondentDTO create(RespondentDTO respondentDTO) {
        var createdRespondent = respondentRepository.save(respondentMapper.asEntity(respondentDTO));
        log.info("create respondent ok id {}", createdRespondent.getId());
        log.trace("create respondent ok  {}", createdRespondent);
        return respondentMapper.asDto(createdRespondent);
    }

    @Override
    public RespondentDTO update(RespondentDTO respondentDTO) {
        if(!respondentRepository.existsById(respondentDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(RESPONDENT_IDENTIFIER_NOT_FOUND_MESSAGE, respondentDTO.getId()));
        }
        var updatedRespondent = respondentRepository.save(respondentMapper.asEntity(respondentDTO));
        log.info("update respondent ok id {}", updatedRespondent.getId());
        log.trace("update respondent ok  {}", updatedRespondent);
        return respondentMapper.asDto(updatedRespondent);
    }

    @Override
    public RespondentDTO read(Long respondentId) {
        var respondent = respondentRepository.findById(respondentId)
                .map(respondentMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(RESPONDENT_IDENTIFIER_NOT_FOUND_MESSAGE, respondentId)));
        log.info("read respondent end ok - Id: {}", respondentId);
        log.trace("read respondent end ok - respondent: {}", respondent);
        return respondent;
    }

    @Override
    public void delete(Long respondentId) {
        if(!respondentRepository.existsById(respondentId)){
            throw new ResourceNotFoundException(MessageFormat.format(RESPONDENT_IDENTIFIER_NOT_FOUND_MESSAGE, respondentId));
        }
        respondentRepository.deleteById(respondentId);
        log.info("delete respondent ok id {}", respondentId);
    }

    @Override
    public Page<RespondentDTO> readAll(Pageable pageable, String firstName, String lastName, String phone, Date startDate, Date endDate, Long fundingId) {
        var respondents = respondentRepository
                .readAllByFiltering(pageable, firstName, lastName, phone, startDate, endDate, fundingId)
                .map(respondentMapper::asDto);
        log.trace("list respondent get ok {}", respondents);
        return respondents;
    }
}
