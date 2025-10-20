package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.enums.StatusType;
import com.webgram.dgpsn.exceptions.NotValidPeriodException;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.HistoryStatusMapper;
import com.webgram.dgpsn.models.HistoryStatusDTO;
import com.webgram.dgpsn.models.responses.StatisticalDTO;
import com.webgram.dgpsn.repositories.HistoryStatusRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.HistoryStatusService;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HistoryStatusServiceImpl implements HistoryStatusService {
    private final HistoryStatusRepository historyStatusRepository;
    private final HistoryStatusMapper historyStatusMapper;
    private final ManagementUnitRepository managementUnitRepository;

    @Override
    public HistoryStatusDTO create(HistoryStatusDTO historyStatusDTO) {
        if(Objects.nonNull(historyStatusDTO.getStartDate()) && Objects.nonNull(historyStatusDTO.getEndDate())) {
            if(historyStatusDTO.getStartDate().compareTo(historyStatusDTO.getEndDate()) > 0) {
                log.info("compare date {} ", historyStatusDTO.getStartDate().compareTo(historyStatusDTO.getEndDate()));
                throw new NotValidPeriodException("la date de début ne peut pas être postérieure à la date de fin {}", historyStatusDTO.getStartDate() );
            }
        }
        var count = historyStatusRepository.getNotClosed(historyStatusDTO.getProjetId(), historyStatusDTO.getId());
        if(count > 0) {
            throw new NotValidPeriodException("Veillez clôturer le statut actuel");
        }
        var countChevauchement = historyStatusRepository.validPeriod(historyStatusDTO.getProjetId(), historyStatusDTO.getId(), historyStatusDTO.getStartDate(), historyStatusDTO.getEndDate());
        if(countChevauchement > 0) {
            throw new NotValidPeriodException("Attention l y' a chevauchement de période");
        }
        var countLast = historyStatusRepository.getLast(historyStatusDTO.getProjetId(), historyStatusDTO.getId(), historyStatusDTO.getStartDate());
        if(countLast  == 0) {
            var project = managementUnitRepository.findById(historyStatusDTO.getProjetId()).get();
//            project.setStatus(StatusEntity.builder().id(historyStatusDTO.getStatusId()).build());
            managementUnitRepository.save(project);
        }
         var savedHistoryStatus = historyStatusRepository.save(historyStatusMapper.asEntity(historyStatusDTO));

        log.info("HistoryStatus successfully added {}", savedHistoryStatus);

        return historyStatusMapper.asDto(savedHistoryStatus);
    }

    @Override
    public HistoryStatusDTO update(HistoryStatusDTO historyStatusDTO) {
        try{
            if(historyStatusRepository.existsById(historyStatusDTO.getId())) {
                if(Objects.nonNull(historyStatusDTO.getStartDate()) && Objects.nonNull(historyStatusDTO.getEndDate())) {
                    if(historyStatusDTO.getStartDate().compareTo(historyStatusDTO.getEndDate()) > 0) {
                        log.info("compare date {} ", historyStatusDTO.getStartDate().compareTo(historyStatusDTO.getEndDate()));
                        throw new NotValidPeriodException("la date de début ne peut pas être postérieure à la date de fin {}" );
                    }
                }
                var count = historyStatusRepository.getNotClosed(historyStatusDTO.getProjetId(), historyStatusDTO.getId());
                if(count > 0) {
                    throw new NotValidPeriodException("Veillez clôturer le statut actuel");
                }
                var countChevauchement = historyStatusRepository.validPeriod(historyStatusDTO.getProjetId(), historyStatusDTO.getId(), historyStatusDTO.getStartDate(), historyStatusDTO.getEndDate());
                if(countChevauchement > 0) {
                    throw new NotValidPeriodException("Attention l y' a chevauchement de période");
                }
                var countLast = historyStatusRepository.getLast(historyStatusDTO.getProjetId(), historyStatusDTO.getId(), historyStatusDTO.getStartDate());
                if(countLast  == 0) {
                    var project = managementUnitRepository.findById(historyStatusDTO.getProjetId()).get();
//                    project.setStatus(StatusEntity.builder().id(historyStatusDTO.getStatusId()).build());
                    managementUnitRepository.save(project);
                }
                var historyStatus = historyStatusMapper.asEntity(historyStatusDTO);

                var updatedHistoryStatus = historyStatusRepository.save(historyStatus);

                log.info("HistoryStatus successfully updated {} ", updatedHistoryStatus.getId());

                return historyStatusMapper.asDto(updatedHistoryStatus);
            } else {
                throw new ResourceNotFoundException("HistoryStatus", historyStatusDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("HistoryStatus", historyStatusDTO.getId());
        }
    }

    @Override
    public HistoryStatusDTO read(Long historyStatusId) {
        var historyStatus = historyStatusRepository
                .findById(historyStatusId)
                .orElseThrow(()-> new ResourceNotFoundException("HistoryStatus", historyStatusId));

        log.info("reading historyStatus id {}", historyStatusId);

        return historyStatusMapper.asDto(historyStatus);
    }

    @Override
    public void delete(Long historyStatusId) {
        try {
            historyStatusRepository.deleteById(historyStatusId);
            log.info("The historyStatus id {} is deleted", historyStatusId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("HistoryStatus", historyStatusId);
        }
    }

    @Override
    public Page<HistoryStatusDTO> readAll(
            Pageable pageable,
            String startDate,
            String endDate,
            Long statusId,
            Long projetId
    )throws ParseException {
        return historyStatusRepository
                .readAllByFiltering(pageable, startDate, endDate, statusId, projetId)
                .map(historyStatusMapper::asDto);
    }

    @Override
    public List<StatisticalDTO> countByStatusCode(StatusType statusType) {
        return historyStatusRepository.countByStatusCode(statusType);
    }

    @Override
    public List<HistoryStatusDTO> readStatusProjectByLibelle(String libelle) {
        return historyStatusMapper.parse(historyStatusRepository.findHistoryStatusEntityByStatusStatusTypeAndAndStatusLibelle(StatusType.PROJECT, libelle));
    }
}
