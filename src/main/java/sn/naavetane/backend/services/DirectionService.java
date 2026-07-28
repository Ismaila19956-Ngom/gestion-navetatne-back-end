package sn.naavetane.backend.services;

import org.springframework.data.domain.Page;
import sn.naavetane.backend.models.DirectionDTO;

import java.util.Map;

public interface DirectionService {
    DirectionDTO create(DirectionDTO directionDTO);
    DirectionDTO update(DirectionDTO directionDTO);
    DirectionDTO read(Long directionId);
    void delete(Long directionId);
    Page<DirectionDTO> readAll(Map<String, String> searchParams, int page, int size);
    Page<DirectionDTO> loadOrganigramme(int page, int size);
}
