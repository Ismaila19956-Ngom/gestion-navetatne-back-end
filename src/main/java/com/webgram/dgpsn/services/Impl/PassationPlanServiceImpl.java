package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PassationPlanMapper;
import com.webgram.dgpsn.models.PassationPlanDTO;
import com.webgram.dgpsn.repositories.PassationPlanRepository;
import com.webgram.dgpsn.services.PassationPlanService;
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
public class PassationPlanServiceImpl implements PassationPlanService {

    private static final String STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE = "id n'existe pas";
    final PassationPlanRepository passationPlanRepository;
    final PassationPlanMapper passationPlanMapper;

    @Override
    public PassationPlanDTO createPassationPlan(PassationPlanDTO passationPlanDTO) {
        var savedpassation = passationPlanRepository.save(passationPlanMapper.asEntity(passationPlanDTO));

        log.info("Structure successfully added {}", savedpassation);

        return passationPlanMapper.asDto(savedpassation);
    }

    @Override
    public PassationPlanDTO updatePassationPlan(PassationPlanDTO passationPlanDTO) {
        if(!passationPlanRepository.existsById(passationPlanDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE, passationPlanDTO.getId()));
        }

        var passation = passationPlanMapper.asEntity(passationPlanDTO);

        var updatedpassation = passationPlanMapper.asDto(passationPlanRepository.save(passation));

        log.info("Structure successfully updated {} ", updatedpassation.getId());

        return updatedpassation;
    }

    @Override
    public PassationPlanDTO readPassationPlan(Long id) {
        var passation = passationPlanRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Plan de Passation", id));

        log.info("reading Passation de Plan id {}", id);

        return passationPlanMapper.asDto(passation);
    }

    @Override
    public void deletePassationPlan(Long id) {
        try {
            passationPlanRepository.deleteById(id);
            log.info("The plan of passation id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<PassationPlanDTO> readAllPassationPlan(Pageable pageable, String reference, String libelle,Long managementUnitId, String sortBy, Boolean ascending) {
        return passationPlanRepository.readAllByFiltering(pageable,reference,libelle,managementUnitId,sortBy,ascending)
                .map(passationPlanMapper::asDto);
    }
}
