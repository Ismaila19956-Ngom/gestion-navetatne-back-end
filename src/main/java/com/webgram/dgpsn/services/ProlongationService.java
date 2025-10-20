package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.ProlongationDTO;


public interface ProlongationService {
    ProlongationDTO create(ProlongationDTO prolongationDTO);
    ProlongationDTO update(ProlongationDTO prolongationDTO);
    ProlongationDTO read(Long prolongationId);
    void delete(Long prolongationId);
    Page<ProlongationDTO> readAll(Pageable pageable, String justification, Integer duration, Long fundingId);
}
