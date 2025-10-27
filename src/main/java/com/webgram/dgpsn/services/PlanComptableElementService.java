package com.webgram.dgpsn.services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.PlanComptableElementDTO;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import java.util.List;

public interface PlanComptableElementService {
    PlanComptableElementDTO create(PlanComptableElementDTO dto);
    PlanComptableElementDTO update(PlanComptableElementDTO dto);
    PlanComptableElementDTO read(Long elementId);
    void delete(Long elementId);
    Page<PlanComptableElementDTO> readAll(
            Pageable pageable,
            List<Long> idsToIgnore,
            String code,
            String libelle,
            TypePlanComptable type,
            String sortBy,
            Boolean ascending
    );
    List<PlanComptableElementDTO> getRealisationsBySousCompteId(Long sousCompteId);
}