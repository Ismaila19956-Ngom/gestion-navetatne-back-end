package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import com.webgram.dgpsn.models.PlanComptableElementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
            Long parentId,
            String sortBy,
            Boolean ascending
    );

    List<PlanComptableElementDTO> getRealisationsByRubriqueId(Long rubriqueId);
    List<PlanComptableElementDTO> getRubriquesByClasseId(Long classeId);

    Page<PlanComptableElementDTO> getClasses(Pageable pageable);
}
