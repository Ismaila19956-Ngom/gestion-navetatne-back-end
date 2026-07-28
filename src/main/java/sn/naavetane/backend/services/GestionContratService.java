package sn.naavetane.backend.services;

import sn.naavetane.backend.models.GestionContratDTO;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface GestionContratService {
    GestionContratDTO create(GestionContratDTO dto);
    @Transactional
    GestionContratDTO updateStatusContrat(Long contratId, boolean isActive);
    @Transactional(readOnly = true)
    List<GestionContratDTO> findByAgentId(Long agentId);
    GestionContratDTO update(Long contratId, GestionContratDTO dto);
    void delete(Long contratId);
    List<GestionContratDTO> searchByAgentId(Long agentId, String typeContratId, LocalDate dateDebut, LocalDate dateFin);
}
