package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QAgentBafEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.AgentBafMapper;
import com.webgram.dgpsn.models.AgentBafDTO;
import com.webgram.dgpsn.repositories.AgentBafRepository;
import com.webgram.dgpsn.services.AgentBafService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AgentBafServiceImpl implements AgentBafService {
    private final AgentBafRepository agentBafRepository;
    private final AgentBafMapper agentBafMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public AgentBafDTO create(AgentBafDTO agentBafDTO) {
        var savedAgentBaf = agentBafRepository.save(agentBafMapper.asEntity(agentBafDTO));
        log.info("AgentBaf successfully added {}", savedAgentBaf);
        return agentBafMapper.asDto(savedAgentBaf);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public AgentBafDTO update(AgentBafDTO agentBafDTO) {
        var agentBafSaved = agentBafMapper.asEntity(agentBafDTO);
        var updatedAgentBaf = agentBafMapper.asDto(agentBafRepository.save(agentBafSaved));
        log.info("AgentBaf successfully updated {}", updatedAgentBaf.getId());
        return updatedAgentBaf;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public AgentBafDTO read(Long agentBafId) {
        var agentBaf = agentBafRepository
                .findById(agentBafId)
                .orElseThrow(() -> new ResourceNotFoundException("AgentBaf", agentBafId));
        log.info("Reading agent baf id {}", agentBafId);
        return agentBafMapper.asDto(agentBaf);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long agentBafId) {
        try {
            agentBafRepository.deleteById(agentBafId);
            log.info("The agent baf id {} is deleted", agentBafId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<AgentBafDTO> readAllAgentBafs(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return agentBafRepository.findAll(booleanBuilder, pageable)
                .map(agentBafMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QAgentBafEntity.agentBafEntity;
            if (searchParams.containsKey("numero"))
                booleanBuilder.and(qEntity.numero.eq(Integer.valueOf(searchParams.get("numero"))));
            if (searchParams.containsKey("prenom"))
                booleanBuilder.and(qEntity.prenom.eq(searchParams.get("prenom")));
            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.eq(searchParams.get("nom")));
            if (searchParams.containsKey("dateNaissanceMatricule"))
                booleanBuilder.and(qEntity.dateNaissanceMatricule.eq(LocalDate.parse(searchParams.get("dateNaissanceMatricule"))));
            if (searchParams.containsKey("typeContratId"))
                booleanBuilder.and(qEntity.typeContrat.id.eq(Long.valueOf(searchParams.get("typeContratId"))));
            if (searchParams.containsKey("dateEntree"))
                booleanBuilder.and(qEntity.dateEntree.eq(LocalDate.parse(searchParams.get("dateEntree"))));
            if (searchParams.containsKey("dateSortie"))
                booleanBuilder.and(qEntity.dateSortie.eq(LocalDate.parse(searchParams.get("dateSortie"))));
            if (searchParams.containsKey("motifSortie"))
                booleanBuilder.and(qEntity.motifSortie.eq(searchParams.get("motifSortie")));
            if (searchParams.containsKey("lieuService"))
                booleanBuilder.and(qEntity.lieuService.eq(searchParams.get("lieuService")));
            if (searchParams.containsKey("posteId"))
                booleanBuilder.and(qEntity.poste.id.eq(Long.valueOf(searchParams.get("posteId"))));
            if (searchParams.containsKey("fonctionId"))
                booleanBuilder.and(qEntity.fonction.id.eq(Long.valueOf(searchParams.get("fonctionId"))));
            if (searchParams.containsKey("profilId"))
                booleanBuilder.and(qEntity.profil.id.eq(Long.valueOf(searchParams.get("profilId"))));
            if (searchParams.containsKey("genre"))
                booleanBuilder.and(qEntity.genre.eq(searchParams.get("genre")));
            if (searchParams.containsKey("diplomeId"))
                booleanBuilder.and(qEntity.diplome.id.eq(Long.valueOf(searchParams.get("profilId"))));
        }
    }
}