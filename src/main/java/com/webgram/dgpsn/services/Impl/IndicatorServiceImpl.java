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
import com.webgram.dgpsn.mappers.IndicatorMapper;
import com.webgram.dgpsn.models.IndicatorDTO;
import com.webgram.dgpsn.repositories.IndicatorRepository;
import com.webgram.dgpsn.services.IndicatorService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class IndicatorServiceImpl implements IndicatorService {
    private final IndicatorRepository indicatorRepository;
    private final IndicatorMapper indicatorMapper;

    @Override
    @Journal(actionType=ActionType.ADD_INDICATEURS_TO_AVANCEMENT)
    public IndicatorDTO create(IndicatorDTO indicatorDTO) {
         var savedIndicator = indicatorRepository.save(indicatorMapper.asEntity(indicatorDTO));

        log.info("indicator successfully added {}", savedIndicator);

        return indicatorMapper.asDto(savedIndicator);
    }

    @Override
    @Journal(actionType=ActionType.UPDATE_INDICATEURS_TO_AVANCEMENT)
    public IndicatorDTO update(IndicatorDTO indicatorDTO) {
        try{
            if(indicatorRepository.existsById(indicatorDTO.getId())) {
                var indicator = indicatorMapper.asEntity(indicatorDTO);

                var updatedIndicator = indicatorRepository.save(indicator);

                log.info("Indicator successfully updated {} ", updatedIndicator.getId());

                return indicatorMapper.asDto(updatedIndicator);
            } else {
                throw new ResourceNotFoundException("Indicator", indicatorDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Indicator", indicatorDTO.getId());
        }
    }

    @Override
    @Journal(actionType=ActionType.READ_INDICATEURS_TO_AVANCEMENT)
    public IndicatorDTO read(Long indicatorId) {
        var indicator = indicatorRepository
                .findById(indicatorId)
                .orElseThrow(()-> new ResourceNotFoundException("Indicator", indicatorId));

        log.info("reading indicator id {}", indicatorId);

        return indicatorMapper.asDto(indicator);
    }

    @Override
    @Journal(actionType=ActionType.DELETE_INDICATEURS_TO_AVANCEMENT)
    public void delete(Long indicatorId) {
        try {
            indicatorRepository.deleteById(indicatorId);
            log.info("The indicator id {} is deleted", indicatorId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Indicator", indicatorId);
        }
    }

    @Override
    @Journal(actionType=ActionType.READ_INDICATEURS_TO_AVANCEMENT)
    public Page<IndicatorDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Long unitId,
            Long indicatorTypeId,
            String sortBy,
            Boolean ascending
    ) {
        return indicatorRepository
                .readAllByFilters(pageable, code, libelle, unitId, indicatorTypeId,sortBy,
                         ascending)
                .map(indicatorMapper::asDto);
    }
}
