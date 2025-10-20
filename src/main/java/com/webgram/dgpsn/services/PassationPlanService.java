package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.PassationPlanDTO;

public interface PassationPlanService {
    PassationPlanDTO createPassationPlan(PassationPlanDTO passationPlanDTO);
    PassationPlanDTO updatePassationPlan(PassationPlanDTO passationPlanDTO);
    PassationPlanDTO readPassationPlan(Long id);
    void deletePassationPlan(Long id);

    Page<PassationPlanDTO> readAllPassationPlan(
            Pageable pageable,
            String reference,
            String libelle,
            Long managementUnitId,
            String sortBy,
            Boolean ascending
    );


}
