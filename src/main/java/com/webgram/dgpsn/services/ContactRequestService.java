package com.webgram.dgpsn.services;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeDemande;
import com.webgram.dgpsn.models.ContactRequestDTO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ContactRequestService {
    ContactRequestDTO create(MultipartFile file, ContactRequestDTO contactRequestDTO) throws IOException;
    ContactRequestDTO read(Long requestId);
    Page<ContactRequestDTO> readAll(
            Pageable pageable,
            List<Long> idsToIgnore,
            TypeDemande requestType,
            String lastName,
            String firstName,
            String email,
            String phone,
            String organization,
            String subject,
            Statut statut,
            Date dateCreation,
            Long serviceId,
            String sortBy,
            Boolean ascending
    );
    void delete(Long requestId);
    void changeStatut(Long requestId, Statut requestStatut);
    Resource downloadFile(Long requestId) throws IOException;
}