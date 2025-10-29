package com.webgram.dgpsn.services;
import com.webgram.dgpsn.models.ParticipantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;

public interface ParticipantService {
    ParticipantDTO create(ParticipantDTO dto);
    ParticipantDTO update(ParticipantDTO dto);
    ParticipantDTO read(Long id);
    void delete(Long id);
    Page<ParticipantDTO> readAll(Map<String, String> searchParams, Pageable pageable);
}
