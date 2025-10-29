package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.ValueIndicatorDTO;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public interface ValueIndicatorService {
    ValueIndicatorDTO create(ValueIndicatorDTO valueIndicatorDTO);
    ValueIndicatorDTO update(ValueIndicatorDTO valueIndicatorDTO);
    ValueIndicatorDTO read(Long id);
    void delete(Long id);
    Page<ValueIndicatorDTO> readAll(
            Pageable pageable,
            Long projetId,
            Long activityId,
            Long indicatorId,
            String period,
            Double targetValue,
            Double valueReched,
            Date startDate,
            Date endDate
    );

    void importIndicator(MultipartFile file, Long projectId);

    void export(PrintWriter writer);
    List<ValueIndicatorDTO> findLast(ManagementUnitEntity projet);
}
