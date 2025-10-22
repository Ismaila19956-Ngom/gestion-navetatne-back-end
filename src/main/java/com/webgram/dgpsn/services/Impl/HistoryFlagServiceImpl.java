package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.NotValidPeriodException;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.HistoryFlagMapper;
import com.webgram.dgpsn.models.HistoryFlagDTO;
import com.webgram.dgpsn.models.responses.StatisticalDTO;
import com.webgram.dgpsn.repositories.HistoryFlagRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.HistoryFlagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HistoryFlagServiceImpl implements HistoryFlagService {
    private final HistoryFlagRepository historyFlagRepository;
    private final HistoryFlagMapper historyFlagMapper;
    private final ManagementUnitRepository managementUnitRepository;

    @Override
    public HistoryFlagDTO create(HistoryFlagDTO historyFlagDTO) {
        if(Objects.nonNull(historyFlagDTO.getStartDate()) && Objects.nonNull(historyFlagDTO.getEndDate())) {
            if(historyFlagDTO.getStartDate().compareTo(historyFlagDTO.getEndDate()) > 0) {
                log.info("compare date {} ", historyFlagDTO.getStartDate().compareTo(historyFlagDTO.getEndDate()));
                throw new NotValidPeriodException("la date de début ne peut pas être postérieure à la date de fin {}");
            }
        }
        var count = historyFlagRepository.getNotClosed(historyFlagDTO.getProjetId(), historyFlagDTO.getId());
        if(count > 0) {
            throw new NotValidPeriodException("Veillez clôturer le flag actuel");
        }
        var countChevauchement = historyFlagRepository.validPeriod(historyFlagDTO.getProjetId(), historyFlagDTO.getId(), historyFlagDTO.getStartDate(), historyFlagDTO.getEndDate());
        if(countChevauchement > 0) {
            throw new NotValidPeriodException("Attention l y' a chevauchement de période");
        }
        var countLast = historyFlagRepository.getLast(historyFlagDTO.getProjetId(), historyFlagDTO.getId(), historyFlagDTO.getStartDate());
        if(countLast  == 0) {
            var project = managementUnitRepository.findById(historyFlagDTO.getProjetId()).get();
//            project.setFlag(FlagEntity.builder().id(historyFlagDTO.getFlagId()).build());
            managementUnitRepository.save(project);
        }
         var savedHistoryFlag = historyFlagRepository.save(historyFlagMapper.asEntity(historyFlagDTO));

        log.info("flag successfully added {}", savedHistoryFlag);

        return historyFlagMapper.asDto(savedHistoryFlag);
    }

    @Override
    public HistoryFlagDTO update(HistoryFlagDTO historyFlagDTO) {
        try{
            if(historyFlagRepository.existsById(historyFlagDTO.getId())) {
                if(Objects.nonNull(historyFlagDTO.getStartDate()) && Objects.nonNull(historyFlagDTO.getEndDate())) {
                    if(historyFlagDTO.getStartDate().compareTo(historyFlagDTO.getEndDate()) > 0) {
                        log.info("compare date {} ", historyFlagDTO.getStartDate().compareTo(historyFlagDTO.getEndDate()));
                        throw new NotValidPeriodException("la date de début ne peut pas être postérieure à la date de fin {}");
                    }
                }
                var count = historyFlagRepository.getNotClosed(historyFlagDTO.getProjetId(), historyFlagDTO.getId());
                if(count > 0) {
                    throw new NotValidPeriodException("Veillez clôturer le flag actuel");
                }
                var countChevauchement = historyFlagRepository.validPeriod(historyFlagDTO.getProjetId(), historyFlagDTO.getId(), historyFlagDTO.getStartDate(), historyFlagDTO.getEndDate());
                if(countChevauchement > 0) {
                    throw new NotValidPeriodException("Attention l y' a chevauchement de période");
                }
                var countLast = historyFlagRepository.getLast(historyFlagDTO.getProjetId(), historyFlagDTO.getId(), historyFlagDTO.getStartDate());
                if(countLast  == 0) {
                    var project = managementUnitRepository.findById(historyFlagDTO.getProjetId()).get();
//                    project.setFlag(FlagEntity.builder().id(historyFlagDTO.getFlagId()).build());
                    managementUnitRepository.save(project);
                }
                var historyFlag = historyFlagMapper.asEntity(historyFlagDTO);

                var updatedHistoryFlag = historyFlagRepository.save(historyFlag);

                log.info("HistoryFlag successfully updated {} ", updatedHistoryFlag.getId());

                return historyFlagMapper.asDto(updatedHistoryFlag);
            } else {
                throw new ResourceNotFoundException("HistoryFlag", historyFlagDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("HistoryFlag", historyFlagDTO.getId());
        }
    }

    @Override
    public HistoryFlagDTO read(Long historyFlagId) {
        var historyFlag = historyFlagRepository
                .findById(historyFlagId)
                .orElseThrow(()-> new ResourceNotFoundException("HistoryFlag", historyFlagId));

        log.info("reading historyFlag id {}", historyFlagId);

        return historyFlagMapper.asDto(historyFlag);
    }

    @Override
    public void delete(Long historyFlagId) {
        try {
            historyFlagRepository.deleteById(historyFlagId);
            log.info("The historyFlag id {} is deleted", historyFlagId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("HistoryFlag", historyFlagId);
        }
    }

    @Override
    public Page<HistoryFlagDTO> readAll(
            Pageable pageable,
            String startDate,
            String endDate,
            Long flagId,
            Long projetId
    ) throws ParseException {
        return historyFlagRepository
                .readAllByFiltering(pageable, startDate, endDate, flagId, projetId)
                .map(historyFlagMapper::asDto);
    }

    @Override
    public Map<String, Long> countByFlagCode() {
        var historyFlags = historyFlagRepository.countByFlagCode();
        var values = new HashMap<String, Long>();
        values.put("SATIS", StatisticalDTO.getStatical("SATIS", historyFlags).getValue());
        values.put("ALT", StatisticalDTO.getStatical("ALT", historyFlags).getValue());
        values.put("BLOC", StatisticalDTO.getStatical("BLOC", historyFlags).getValue());
        return values;
    }

    @Override
    public List<HistoryFlagDTO> readByFlagCode(String code) {
        return historyFlagMapper.parse(historyFlagRepository.findHistoryFlagEntityByFlagCode(code));
    }

}