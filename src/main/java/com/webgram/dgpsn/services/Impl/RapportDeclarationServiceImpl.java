package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.entities.QRapportEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RapportMapper;
import com.webgram.dgpsn.models.RapportDTO;
import com.webgram.dgpsn.repositories.RapportRepository;
import com.webgram.dgpsn.services.RapportDeclarationService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RapportDeclarationServiceImpl implements RapportDeclarationService {
    private final RapportRepository rapportRepository;
    private final RapportMapper rapportMapper;

    @Override
    public RapportDTO create(RapportDTO rapportDTO) {
        var rapport = rapportMapper.asEntity(rapportDTO);
        var savedRapport = rapportRepository.save(rapport);
        log.info("Rapport saved successfully {}", savedRapport.getId());
        return rapportMapper.asDto(savedRapport);
    }

    @Override
    public RapportDTO update(RapportDTO rapportDTO) {
        var rapport = rapportMapper.asEntity(rapportDTO);
        var updatedRapport = rapportRepository.save(rapport);
        log.info("Rapport updated successfully {}", updatedRapport.getId());
        return rapportMapper.asDto(updatedRapport);
    }

    @Override
    public RapportDTO read(Long rapportId) {
        var rapport = rapportRepository.findById(rapportId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Rapport with id [%s] not found", rapportId)));
        return rapportMapper.asDto(rapport);
    }

    @Override
    public void delete(Long rapportId) {
        read(rapportId);
        rapportRepository.deleteById(rapportId);
    }

    @Override
    public Page<RapportDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return rapportRepository.findAll(booleanBuilder, pageable)
                .map(rapportMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QRapportEntity.rapportEntity;
            if (searchParams.containsKey("dateDebut"))
                booleanBuilder.and(qEntity.dateDebut.eq(LocalDate.parse(searchParams.get("dateDebut"))));
            if (searchParams.containsKey("dateFin"))
                booleanBuilder.and(qEntity.dateFin.eq(LocalDate.parse(searchParams.get("dateFin"))));
            if (searchParams.containsKey("contenu"))
                booleanBuilder.and(qEntity.contenu.containsIgnoreCase(searchParams.get("contenu")));
            if (searchParams.containsKey("declarationId"))
                booleanBuilder.and(qEntity.declaration.id.eq(Long.parseLong(searchParams.get("declarationId"))));
        }

    }
}
