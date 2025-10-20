package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SubSectorDTO;

public interface SubSectorService {
    SubSectorDTO createSubSector(SubSectorDTO subSectorDTO);
    SubSectorDTO updateSubSector(SubSectorDTO subSectorDTO);
    SubSectorDTO readSubSector(Long id);
    SubSectorDTO readSubSectorByCode(String code);
    void deleteSubSector(Long id);
    Page<SubSectorDTO> readAllSubSector(Pageable pageable, String code, String libelle, Long sectorId);

}
