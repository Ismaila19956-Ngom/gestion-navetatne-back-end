package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.MilestoneDTO;

import java.io.PrintWriter;
import java.text.ParseException;

public interface MilestoneService {
    MilestoneDTO create(MilestoneDTO milestoneDTO);
    MilestoneDTO update(MilestoneDTO milestoneDTO);
    MilestoneDTO read(Long milestoneId);
    void delete(Long milestoneId);
    Page<MilestoneDTO> readAll(
            Pageable pageable,
            String libelle,
            String predicatedDate,
            String readDate,
            Long projetId
    ) throws ParseException;
    void importMilestone(MultipartFile file, Long projectId);
    void exportMilsstone(PrintWriter writer);
}
