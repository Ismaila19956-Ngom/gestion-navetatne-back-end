package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ProlongationMapper;
import com.webgram.dgpsn.models.ProlongationDTO;
import com.webgram.dgpsn.repositories.ProlongationRepository;
import com.webgram.dgpsn.services.ProlongationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProlongationServiceImpl implements ProlongationService {
    private final ProlongationRepository prolongationRepository;
    private final ProlongationMapper prolongationMapper;

    String PROLONGATION_IDENTIFIER_NOT_FOUND_MESSAGE = "In valid id prolongation {0}";

    @Override
    public ProlongationDTO create(ProlongationDTO prolongationDTO) {
        var createdProlongation = prolongationRepository.save(prolongationMapper.asEntity(prolongationDTO));
        log.info("create prolongation ok id {}", createdProlongation.getId());
        log.trace("create prolongation ok  {}", createdProlongation);
        return prolongationMapper.asDto(createdProlongation);
    }

    @Override
    public ProlongationDTO update(ProlongationDTO prolongationDTO) {
        if(!prolongationRepository.existsById(prolongationDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(PROLONGATION_IDENTIFIER_NOT_FOUND_MESSAGE, prolongationDTO.getId()));
        }
        var updatedProlongation = prolongationRepository.save(prolongationMapper.asEntity(prolongationDTO));
        log.info("update prolongation ok id {}", updatedProlongation.getId());
        log.trace("update prolongation ok  {}", updatedProlongation);
        return prolongationMapper.asDto(updatedProlongation);
    }

    @Override
    public ProlongationDTO read(Long prolongationId) {
        var prolongation = prolongationRepository.findById(prolongationId)
                .map(prolongationMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(PROLONGATION_IDENTIFIER_NOT_FOUND_MESSAGE, prolongationId)));
        log.info("read prolongation end ok - Id: {}", prolongationId);
        log.trace("read prolongation end ok - prolongation: {}", prolongation);
        return prolongation;
    }

    @Override
    public void delete(Long prolongationId) {
        if(!prolongationRepository.existsById(prolongationId)){
            throw new ResourceNotFoundException(MessageFormat.format(PROLONGATION_IDENTIFIER_NOT_FOUND_MESSAGE, prolongationId));
        }
        prolongationRepository.deleteById(prolongationId);
        log.info("delete prolongation ok id {}", prolongationId);

    }

    @Override
    public Page<ProlongationDTO> readAll(Pageable pageable, String justification, Integer duration, Long fundingId) {
        var prolongations = prolongationRepository
                .readAllByFiltering(pageable, justification, duration, fundingId)
                .map(prolongationMapper::asDto);
        log.trace("list prolongation get ok {}", prolongations);
        return prolongations;
    }
}
