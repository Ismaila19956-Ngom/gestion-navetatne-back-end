package sn.naavetane.backend.services.Impl;

import sn.naavetane.backend.exceptions.ResourceNotFoundException;
import sn.naavetane.backend.mappers.GestionContratMapper;
import sn.naavetane.backend.models.GestionContratDTO;
import sn.naavetane.backend.repositories.GestionContratRepository;
import sn.naavetane.backend.services.GestionContratService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GestionContratServiceImpl implements GestionContratService {
    private final GestionContratRepository gestionContratRepository;
    private final GestionContratMapper gestionContratMapper;

    @Override
    public GestionContratDTO create(GestionContratDTO dto) {
        var entity = gestionContratMapper.asEntity(dto);
        var saved = gestionContratRepository.save(entity);
        return gestionContratMapper.asDto(saved);
    }

    @Override
    public GestionContratDTO updateStatusContrat(Long contratId, boolean isActive) {
        var entity = gestionContratRepository.findById(contratId)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", contratId));
        entity.setIsActive(isActive);
        var saved = gestionContratRepository.save(entity);
        return gestionContratMapper.asDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GestionContratDTO> findByAgentId(Long agentId) {
        return gestionContratRepository.findByAgent_Id(agentId).stream()
                .map(gestionContratMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public GestionContratDTO update(Long contratId, GestionContratDTO dto) {
        if (!gestionContratRepository.existsById(contratId)) {
            throw new ResourceNotFoundException("Contrat", contratId);
        }
        var entity = gestionContratMapper.asEntity(dto);
        entity.setId(contratId);
        var saved = gestionContratRepository.save(entity);
        return gestionContratMapper.asDto(saved);
    }

    @Override
    public void delete(Long contratId) {
        if (!gestionContratRepository.existsById(contratId)) {
            throw new ResourceNotFoundException("Contrat", contratId);
        }
        gestionContratRepository.deleteById(contratId);
    }

    @Override
    public List<GestionContratDTO> searchByAgentId(Long agentId, String typeContratId, LocalDate dateDebut, LocalDate dateFin) {
        // Implementation simplifiée, utiliser Custom Repository ou QueryDSL si nécessaire.
        return findByAgentId(agentId);
    }
}
