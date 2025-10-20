package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.DocumentPublicDTO;
import com.webgram.dgpsn.models.DownloadFile;

import java.io.IOException;

public interface DocumentPublicService {
    DocumentPublicDTO create(String documentPublicDTO, MultipartFile file) throws IOException;
    DocumentPublicDTO update(Long id, DocumentPublicDTO documentPublicDTO);
    DocumentPublicDTO read(Long documentId);
    DownloadFile readFile(Long id);
    void delete(Long documentId);
    Page<DocumentPublicDTO> readAll(
            Pageable pageable, String titre, String date, String authors,
            String themes, Long documentTypeId, Long folderId, Boolean publish,
            Long partnerId, Long sectorId
    );
    Page<DocumentPublicDTO> readPublishedDocument(
            Pageable pageable, String titre, String date, String authors,
            String themes, String documentType, String partner, String sector
    );
    void publishOrUnpublish(Long documentId);
}
