package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CessationPerduMapper;
import com.webgram.dgpsn.models.CessationPerduDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.CessationPerduService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CessationPerduServiceImpl implements CessationPerduService {
    private final CessationPerduRepository cessationPerduRepository;
    private final CessationPerduMapper cessationPerduMapper;
    private static final String FORMATION_NOT_FOUND = "La cessation n'existe pas";


    @Override
    public CessationPerduDTO readPerdu(Long cessationId) {
        var cessation = cessationPerduRepository.findById(cessationId).orElseThrow(() -> new ResourceNotFoundException(FORMATION_NOT_FOUND));
        return cessationPerduMapper.asDto(cessation);
    }

    @Override
    public List<CessationPerduDTO> readAll() {
        return cessationPerduRepository.findAll().stream().map(cessationPerduMapper::asDto).collect(Collectors.toList());
    }

    @Override
    public Page<CessationPerduDTO> readPage(Map<String, String> searchParams, int page, int size) throws ParseException {
        var booleanBuilder = new BooleanBuilder();
        if (Objects.nonNull(searchParams)) {
            var qConge = QCessationPerduEntity.cessationPerduEntity;

            if (searchParams.containsKey("libelle"))
                booleanBuilder.and(qConge.libelle.containsIgnoreCase(searchParams.get("libelle")));

            if (searchParams.containsKey("description"))
                booleanBuilder.and(qConge.description.containsIgnoreCase(searchParams.get("description")));

            if (searchParams.containsKey("dateCessation")) {
                var date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateCessation"));
                booleanBuilder.and(qConge.dateCessation.eq(date));
            }

            String typeConge = searchParams.get("typeConge");
            if (typeConge != null && !typeConge.isEmpty()) {
                booleanBuilder.and(qConge.typeConge.stringValue().lower().containsIgnoreCase(typeConge.toLowerCase()));
            }
            String congeIdStr = searchParams.get("congeId");
            if (congeIdStr != null && !congeIdStr.isEmpty()) {
                Long congeId = Long.valueOf(congeIdStr);
                if (congeId != null && congeId != 0) {
                    booleanBuilder.and(qConge.conge.id.eq(congeId));
                }
            }

        }
        Sort sort = Sort.by(Sort.Order.desc("id"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<CessationPerduEntity> cessationPerduEntities = cessationPerduRepository.findAll(booleanBuilder, pageRequest);
        return cessationPerduMapper.asPage(cessationPerduEntities);
    }
    @Override
    public void delete(Long cessationId) {
        try {
            cessationPerduRepository.deleteById(cessationId);
            log.info("The agent id {} is deleted", cessationId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }





}