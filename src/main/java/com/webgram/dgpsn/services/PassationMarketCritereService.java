package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.PassationMarketCritereDTO;

import java.util.List;

public interface PassationMarketCritereService {

    List<PassationMarketCritereDTO> readAllCriterePerMarket(long id);
    List<PassationMarketCritereDTO> createPassationMarketCritere(List<PassationMarketCritereDTO> passationMarketCritereDTO);
    PassationMarketCritereDTO updatePassationMarketCritere(PassationMarketCritereDTO passationMarketCritereDTO);
    PassationMarketCritereDTO readPassationMarketCritere(Long id);
    void deletePassationMarketCritere(Long id);

    Page<PassationMarketCritereDTO> readAllPassationMarketCritere(
            Pageable pageable,
            Double ponderation,
            String expectedValue,
            Long passationMarketId,
            String sortBy,
            Boolean ascending
    );


}
