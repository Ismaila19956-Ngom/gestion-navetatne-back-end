package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.CompletionRateEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.enums.Period;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CompletionRateMapper;
import com.webgram.dgpsn.mappers.ManagementUnitMapper;
import com.webgram.dgpsn.models.CompletionRateDTO;
import com.webgram.dgpsn.models.responses.CompletionRateResponse;
import com.webgram.dgpsn.repositories.CompletionRateRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.CompletionRateService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompletionRateServiceImpl implements CompletionRateService {
    private final CompletionRateRepository completionRateRepository;
    private final CompletionRateMapper completionRateMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final ManagementUnitMapper managementUnitMapper;


    private String COMPLETION_RATE_NOT_FOUND_MESSAGE = "Invalide id completion rate: {}";

    @Override
    public CompletionRateDTO create(CompletionRateDTO completionRateDTO) {
        var createdCompletionRate = completionRateRepository.save(completionRateMapper.asEntity(completionRateDTO));

        log.info("createdCompletionRate end ok - create createdCompletionRateId: {}", createdCompletionRate.getId());
        log.trace("createdCompletionRate end ok - create createdCompletionRate: {}", createdCompletionRate);

        return completionRateMapper.asDto(createdCompletionRate);
    }

    @Override
    public CompletionRateDTO update(CompletionRateDTO completionRateDTO) {
        if(!completionRateRepository.existsById(completionRateDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(COMPLETION_RATE_NOT_FOUND_MESSAGE, completionRateDTO.getId()));
        }

        var updatedCompletionRate = completionRateRepository.save(completionRateMapper.asEntity(completionRateDTO));

        log.info("updatedCompletionRate end ok - create CompletionRateId: {}", updatedCompletionRate.getId());
        log.trace("updatedCompletionRate end ok - create CompletionRate: {}", updatedCompletionRate);

        return completionRateMapper.asDto(updatedCompletionRate);
    }

    @Override
    public CompletionRateDTO read(Long id) {
        var completionRate = completionRateRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(MessageFormat.format(COMPLETION_RATE_NOT_FOUND_MESSAGE, id)));

        log.info("read CompletionRate end ok - Id: {}", id);
        log.trace("read CompletionRate end ok - CompletionRate: {}", completionRate);;

        return completionRateMapper.asDto(completionRate);
    }

    @Override
    public void delete(Long id) {
        if(!completionRateRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(COMPLETION_RATE_NOT_FOUND_MESSAGE, id));
        }
        completionRateRepository.deleteById(id);
        log.info("delete CompletionRate ok id {}", id);
    }

    @Override
    public Page<CompletionRateDTO> readAll(Pageable pageable, Long projetId
            , Period period, Double targetValue, Double valueReched, Integer year, Date startDate, Date endDate) {
        return completionRateRepository
                .readAllByFilters(pageable, projetId, period, targetValue, valueReched, year, startDate, endDate)
                .map(completionRateMapper::asDto);
    }

    @Override
    public CompletionRateResponse readCompletionRateByManagementUnit(Long managementUnitId, Integer year) {
        var managementUnit = managementUnitRepository
                .findById(managementUnitId)
                .orElseThrow(()-> new ResourceNotFoundException("ManagementUnit", managementUnitId));
        List<Period> allPeriod = List.of(Period.T1, Period.T2,Period.T3,Period.T4);
        if(TypeProjet.PROGRAMME.equals(managementUnit.getType())) {
            var completionResponse = builderCompletionRateResponse(managementUnit,year, allPeriod);
            var listCompletionRatesResponsesProgramme = new ArrayList<CompletionRateResponse>();
            var projects = managementUnitRepository.findByParent(managementUnit);
            projects.forEach(aProject -> {
                var completionResponseProject = builderCompletionRateResponse(aProject,year, allPeriod);
                var listCompletionRatesResponsesProject = new ArrayList<CompletionRateResponse>();
                var activities = managementUnitRepository.findByParent(aProject);
                activities.forEach(aActivity -> {
                    var completionResponseActivity = builderCompletionRateResponse(aActivity,year, allPeriod);
                    listCompletionRatesResponsesProject.add(completionResponseActivity);
                });
                completionResponseProject.setChildren(listCompletionRatesResponsesProject);
                listCompletionRatesResponsesProgramme.add(completionResponseProject);
            });
            completionResponse.setChildren(listCompletionRatesResponsesProgramme);
            return completionResponse;
        }else if(TypeProjet.PROJECT.equals(managementUnit.getType())) {
            var completionResponse = builderCompletionRateResponse(managementUnit,year, allPeriod);
            var listCompletionRatesResponses = new ArrayList<CompletionRateResponse>();
            var activities = managementUnitRepository.findByParent(managementUnit);
            activities.forEach(aActivity -> {
                var completionResponseActivity = builderCompletionRateResponse(aActivity,year, allPeriod);
                listCompletionRatesResponses.add(completionResponseActivity);
            });
            completionResponse.setChildren(listCompletionRatesResponses);
            return completionResponse;
        }else if(TypeProjet.ACTIVITY.equals(managementUnit.getType())) {
            var completionResponse = builderCompletionRateResponse(managementUnit,year, allPeriod);
            return completionResponse;
        }else {
            return CompletionRateResponse.builder().managementUnit(managementUnitMapper.asDto(managementUnit)).build();
        }


    }
    public List<CompletionRateDTO> getCompletionRates(ManagementUnitEntity managementUnit, Integer year, List<Period> allPeriod) {
        var addedCompletionRateForProgramme = completionRateRepository.findByManagementUnitAndYear(ManagementUnitEntity.builder().id(managementUnit.getId()).build(), year);
        var periodOnresult = addedCompletionRateForProgramme
                .stream().map(c -> c.getPeriod()).collect(Collectors.toList() );
        var absentPeriods = allPeriod.stream()
                .filter(element -> !periodOnresult.contains(element) ).collect(Collectors.toList());
        absentPeriods.forEach(aPeriod -> {
            addedCompletionRateForProgramme.add(
                    completionRateRepository.save(
                            CompletionRateEntity
                                    .builder()
                                    .managementUnit(managementUnit)
                                    .period(aPeriod)
                                    .targetValue(0.0)
                                    .valueReched(0.0)
                                    .year(year)
                                    .build()
                    ));
        });
        return addedCompletionRateForProgramme.stream().map(completionRateMapper::asDto).collect(Collectors.toList());
    }
    private CompletionRateResponse builderCompletionRateResponse(ManagementUnitEntity managementUnit, Integer year, List<Period> allPeriod){
        var completionResponse = CompletionRateResponse
                .builder().managementUnit(managementUnitMapper.asDto(managementUnit)).build();
        var addedCompletionRate = getCompletionRates(managementUnit,year, allPeriod);
        completionResponse.setCompletionRates(addedCompletionRate);
        return completionResponse;
    }

}
