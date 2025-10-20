package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.FundingDTO;
import com.webgram.dgpsn.models.responses.TauxDecaissementAnnuelDTO;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;

public interface FundingService {
    FundingDTO create(FundingDTO fundingDTO);
    FundingDTO update(FundingDTO fundingDTO);
    FundingDTO read(Long fundingId);
    void delete(Long fundingId);
    Page<FundingDTO> readAll(
            Pageable pageable,
            String financingAgreement,
            Double amount,
            String cash,
            Double rate,
            Double equivalence,
            String approvalDate,
            String closingDate,
            String extentionDate,
            Long fundingTypeId,
            Long projetId,
            Long structureId,
            String sortBy,
            Boolean ascending
    )throws ParseException;
    List<TauxDecaissementAnnuelDTO> getTauxDecaissement(Long projectId);

    Long readTotalFinancement();

    void importFunding(MultipartFile file, Long projectId);

    void export(PrintWriter writer, Long projetId);
}


