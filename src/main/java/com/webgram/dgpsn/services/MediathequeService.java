package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.MediathequeType;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.MediathequeDTO;

import java.io.IOException;
import java.text.ParseException;

public interface MediathequeService {
    MediathequeDTO create(MultipartFile file, String mediatheque) throws IOException;
    MediathequeDTO update(MultipartFile file, MediathequeDTO mediathequeDTO) throws IOException;
    MediathequeDTO read(Long mediathequeId);
    void delete(Long mediathequeId);
    DownloadFile readFile(Long id);
    Page<MediathequeDTO> readAll(
            Pageable pageable,
            String libelle,
            String date,
            MediathequeType mediathequeType,
            Long projetId,
            Long entrepriseId
    )throws ParseException;
}
