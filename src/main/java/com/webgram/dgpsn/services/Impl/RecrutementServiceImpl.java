package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.QRecrutementEntity;
import com.webgram.dgpsn.entities.QRegionEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RecrutementMapper;
import com.webgram.dgpsn.models.RecrutementDTO;
import com.webgram.dgpsn.repositories.RecrutementRepository;
import com.webgram.dgpsn.repositories.UgpProjetRepository;
import com.webgram.dgpsn.services.RecrutementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RecrutementServiceImpl implements RecrutementService {
    private final RecrutementRepository recrutementRepository;
    private final RecrutementMapper recrutementMapper;

    private final UgpProjetRepository ugpProjetRepository;

    String ROLE_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id recrutement {0}";

    @Override
    public RecrutementDTO createRecrutement(RecrutementDTO recrutementDTO) {
        var entity = recrutementMapper.asEntity(recrutementDTO);
        var savedEntity = recrutementRepository.save(entity);
        return recrutementMapper.asDto(savedEntity);
    }

    @Override
    public RecrutementDTO updateRecrutement(RecrutementDTO recrutementDTO) {
        return null;
    }


    @Override
    public void deleteRecrutement(Long id) {
        if(!recrutementRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(ROLE_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        recrutementRepository.deleteById(id);
        log.info("delete recrutement ok id {}", id);
    }

    @Override
    public RecrutementDTO getRecrutement(Long id) {
        var entity = recrutementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return recrutementMapper.asDto(entity);    }

    @Override
    public Page<RecrutementDTO> getAllRecrutements(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return recrutementRepository.findAll(booleanBuilder, pageable)
                .map(recrutementMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QRecrutementEntity.recrutementEntity;
            if (searchParams.containsKey("libelle"))
                booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
        }
    }

}
