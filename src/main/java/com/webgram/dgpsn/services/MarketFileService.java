package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.MarketFileDTO;

import java.util.List;

public interface MarketFileService {
    MarketFileDTO createMarketFile(MarketFileDTO marketFileDTO);
    MarketFileDTO updateMarketFile(MarketFileDTO marketFileDTO);
    MarketFileDTO readMarketFile(Long id);
    void deleteMarketFile(Long id);

    List<MarketFileDTO> readAllMarketWithNtes();
    Page<MarketFileDTO> readAllMarketFile(
            Pageable pageable,
            String fileNumber,
            String email,
            Long passationMarketId,
            String sortBy,
            Boolean ascending
    );


}
