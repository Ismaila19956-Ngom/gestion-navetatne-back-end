package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.PassationMarketDTO;

public interface PassationMarketService {
    PassationMarketDTO createPassationMarket(PassationMarketDTO passationMarketDTO);
    PassationMarketDTO updatePassationMarket(PassationMarketDTO passationMarketDTO);
    PassationMarketDTO readPassationMarket(Long id);
    void deletePassationMarket(Long id);

    Page<PassationMarketDTO> readAllPassationMarket(
            Pageable pageable,
            String reference,
            String libelle,
            Long passationPlanId,
            String sortBy,
            Boolean ascending
    );


}
