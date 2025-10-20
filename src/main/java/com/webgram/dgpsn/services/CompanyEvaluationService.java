package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.CompanyEvaluationDTO;

import java.util.List;
import java.util.Map;

public interface CompanyEvaluationService {
    CompanyEvaluationDTO create(CompanyEvaluationDTO companyEvaluationDTO);
    CompanyEvaluationDTO update(CompanyEvaluationDTO companyEvaluationDTO);
    CompanyEvaluationDTO read(Long companyEvaluationId);
    void delete(Long companyEvaluationId);
    Page<CompanyEvaluationDTO> readAllCompanyEvaluations(Map<String, String> searchParams, Pageable pageable);
    List<CompanyEvaluationDTO> createMultiple(List<CompanyEvaluationDTO> companyEvaluationDTOs);
}