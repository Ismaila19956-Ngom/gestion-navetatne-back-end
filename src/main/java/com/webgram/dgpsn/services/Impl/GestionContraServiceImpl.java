package com.webgram.dgpsn.services.Impl;


import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.GestionContratEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.GestionContratMapper;
import com.webgram.dgpsn.models.GestionContratDTO;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.GestionContratRepository;
import com.webgram.dgpsn.services.GestionContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GestionContraServiceImpl implements GestionContratService {

    private final GestionContratRepository contratRepository;
    private final AgentRepository agentRepository;
    private final GestionContratMapper contratMapper;

    @Override
    public GestionContratDTO create(GestionContratDTO dto) {

        AgentEntity agent = agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format("Agent avec id {0} introuvable", dto.getAgentId())
                ));

        GestionContratEntity entity = contratMapper.asEntity(dto);
        entity.setAgent(agent);

        // Si le contrat doit être actif, désactiver les autres
        if (dto.getIsActive()) {
            deactivateOtherActiveContracts(agent, null);
        }

        GestionContratEntity saved = contratRepository.save(entity);
        return contratMapper.asDto(saved);
    }

    @Transactional
    @Override
    public GestionContratDTO updateStatusContrat(Long contratId, boolean isActive) {

        GestionContratEntity contrat = contratRepository.findById(contratId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format("Contrat avec id {0} introuvable", contratId)
                ));

        if (isActive) {
            deactivateOtherActiveContracts(contrat.getAgent(), contratId);
        }

        contrat.setIsActive(isActive);
        GestionContratEntity updated = contratRepository.save(contrat);

        return contratMapper.asDto(updated);
    }


    @Transactional(readOnly = true)
    @Override
    public List<GestionContratDTO> findByAgentId(Long agentId) {
        List<GestionContratEntity> contrats = contratRepository.findByAgent_Id(agentId);
        return contrats.stream()
                .map(contratMapper::asDto)
                .collect(Collectors.toList());
    }


    /**
     * Désactive tous les contrats actifs d'un agent sauf celui passé en paramètre
     */
    private void deactivateOtherActiveContracts(AgentEntity agent, Long contratIdToKeep) {
        List<GestionContratEntity> autresContratsActifs = contratRepository
                .findByAgent_IdAndIsActive(agent.getId(), true);
        for (GestionContratEntity c : autresContratsActifs) {
            if (!c.getId().equals(contratIdToKeep)) {
                c.setIsActive(false);
                contratRepository.save(c);
            }
        }
    }



    @Override
    public GestionContratDTO update(Long contratId, GestionContratDTO dto) {
        GestionContratEntity contrat = contratRepository.findById(contratId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format("Contrat avec id {0} introuvable", contratId)));
        AgentEntity agent = agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format("Agent avec id {0} introuvable", dto.getAgentId())));
        contrat.setAgent(agent);
        contrat.setDateDebut(dto.getDateDebut());
        contrat.setDateFin(dto.getDateFin());
        contrat.setDescription(dto.getDescription());
        contrat.setTypeContrat(new LabelEntity(dto.getTypeContratId(), null, null, null));
        contrat.setIsActive(dto.getIsActive());
        if (dto.getIsActive()) {
            deactivateOtherActiveContracts(agent, contratId);
        }
        GestionContratEntity updated = contratRepository.save(contrat);
        return contratMapper.asDto(updated);
    }

    @Override
    public void delete(Long contratId) {
        GestionContratEntity contrat = contratRepository.findById(contratId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format("Contrat avec id {0} introuvable", contratId)));
        contratRepository.delete(contrat);
    }

    @Override
    public List<GestionContratDTO> searchByAgentId(Long agentId, String typeContratId, LocalDate dateDebut, LocalDate dateFin) {
        List<GestionContratEntity> contrats = contratRepository.findByAgent_Id(agentId);
        return contrats.stream()
                .filter(c -> typeContratId == null || c.getTypeContrat().getId().equals(Long.valueOf(typeContratId)))
                .filter(c -> dateDebut == null || c.getDateDebut().equals(dateDebut))
                .filter(c -> dateFin == null || c.getDateFin() != null && c.getDateFin().equals(dateFin))
                .map(contratMapper::asDto)
                .collect(Collectors.toList());
    }
}

