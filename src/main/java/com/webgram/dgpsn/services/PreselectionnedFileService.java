package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.PreselectionnedFileDTO;

import java.util.List;

public interface PreselectionnedFileService {
   List<PreselectionnedFileDTO> createPreselectionnedFile(List<PreselectionnedFileDTO> preselectionnedFileDTO);
    PreselectionnedFileDTO updatePreselectionnedFile(PreselectionnedFileDTO preselectionnedFileDTO);
    PreselectionnedFileDTO readPreselectionnedFile(Long id);
    void deletePreselectionnedFile(Long id);

    Page<PreselectionnedFileDTO> readAllPreselectionnedFile(
            Pageable pageable,
            Long passationMarketId,
            String sortBy,
            Boolean ascending
    );


}
