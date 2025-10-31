package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CaracteristiqueRecrutementMapper;
import com.webgram.dgpsn.mappers.CessationPerduMapper;
import com.webgram.dgpsn.models.CaracteristiqueRecrutementDTO;
import com.webgram.dgpsn.repositories.CaracteristiqueRecrutementRepository;
import com.webgram.dgpsn.repositories.CessationPerduRepository;
import com.webgram.dgpsn.services.AlerteService;
import com.webgram.dgpsn.services.CaracteristiqueRecrutementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CaracteristiqueRecrutementServiceImpl implements CaracteristiqueRecrutementService {
    private final CaracteristiqueRecrutementRepository caracteristiqueRecrutementRepository;
    private final CaracteristiqueRecrutementMapper caracteristiqueRecrutementMapper;
    private final CessationPerduMapper cessationPerduMapper;
    private final AlerteService alerteService;
    private final CessationPerduRepository solderestantRepository;
    private static final String NUMERO_FICHE_PREFIX = "DOD";
    private static final String FORMATION_NOT_FOUND = "La cessation n'existe pas";
    private static final String AGENT_NOT_FOUND = "L'agent n'existe pas";


    @Override
    public CaracteristiqueRecrutementDTO create(CaracteristiqueRecrutementDTO caracteristiqueRecrutementDTO) {
        var entity = caracteristiqueRecrutementMapper.asEntity(caracteristiqueRecrutementDTO);
        var savedEntity = caracteristiqueRecrutementRepository.save(entity);
        return caracteristiqueRecrutementMapper.asDto(savedEntity);    }

    @Override
    public CaracteristiqueRecrutementDTO update(CaracteristiqueRecrutementDTO caracteristiqueRecrutementDTO) {
        var entity = caracteristiqueRecrutementMapper.asEntity(caracteristiqueRecrutementDTO);
        var updatedEntity = caracteristiqueRecrutementRepository.save(entity);
        return caracteristiqueRecrutementMapper.asDto(updatedEntity);
    }

    @Override
    public CaracteristiqueRecrutementDTO read(Long cessationId) {
        var cessation = caracteristiqueRecrutementRepository.findById(cessationId).orElseThrow(() -> new ResourceNotFoundException(FORMATION_NOT_FOUND));
        return caracteristiqueRecrutementMapper.asDto(cessation);
    }

    @Override
    public List<CaracteristiqueRecrutementDTO> readAll() {
        return caracteristiqueRecrutementRepository.findAll().stream().map(caracteristiqueRecrutementMapper::asDto).collect(Collectors.toList());
    }

    @Override
    public Page<CaracteristiqueRecrutementDTO> readPageCcaracteristiqueRecrutement(Map<String, String> searchParams, int page, int size) throws ParseException {
        var booleanBuilder = new BooleanBuilder();
        if (Objects.nonNull(searchParams)) {
            var qCaaracteristiqueRecrutement = QCaracteristiqueRecrutementEntity.caracteristiqueRecrutementEntity;

            if (searchParams.containsKey("code"))
                booleanBuilder.and(qCaaracteristiqueRecrutement.code.containsIgnoreCase(searchParams.get("libelle")));

            if (searchParams.containsKey("libelle"))
                booleanBuilder.and(qCaaracteristiqueRecrutement.libelle.containsIgnoreCase(searchParams.get("description")));

        }

        Sort sort = Sort.by(Sort.Order.desc("id"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<CaracteristiqueRecrutementEntity> caracteristiqueRecrutementEntities = caracteristiqueRecrutementRepository.findAll(booleanBuilder, pageRequest);
        return caracteristiqueRecrutementMapper.asPage(caracteristiqueRecrutementEntities);
    }

    @Override
    public void delete(Long cessationId) {
        try {
            caracteristiqueRecrutementRepository.deleteById(cessationId);
            log.info("The agent id {} is deleted", cessationId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

//    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
//        if (Objects.nonNull(searchParams)) {
//            var qEntity = QCaracteristiqueRecrutementEntity.caracteristiquerecrutementEntity;
//            if (searchParams.containsKey("libelle"))
//                booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
//
//            if (searchParams.containsKey("code"))
//                booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
//
//            if (searchParams.containsKey("recrutement"))
//                booleanBuilder.and(qEntity.RecrutementEntity.recrutement.containsIgnoreCase(searchParams.get("recrutement")));

//        }
//    }
//

}
