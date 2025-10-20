package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;
import com.webgram.dgpsn.models.GeographicalLocationDTO;
import com.webgram.dgpsn.models.responses.ReportingByRegionDTO;

import java.util.List;


public interface GeographicalLocationService {
    void createGeographicalLocation(List<GeographicalLocationDTO> geographicalLocationDTO);

    GeographicalLocationDTO readGeographicalLocation(Long id);
    void deleteGeographicalLocation(Long id);
    Page<GeographicalLocationDTO> readAllGeographicalLocation(Pageable pageable, String code, String libelle, CadreLogiqueType cadreLogiqueType, Long projetId);

    List<ReportingByRegionDTO> getReportingBySRegion(Long regionId);
}
