package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.DocumentDto;
import com.webgram.dgpsn.models.DownloadFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface DocumentService {
    DocumentDto createDocument(MultipartFile file, String document) throws IOException;
    DocumentDto updateDocument(MultipartFile file, DocumentDto document);
    DocumentDto readDocument(Long id);
    void deleteDocument(Long conditionnalityId);

    Page<DocumentDto> readAllDocument(Map<String,String> searchParams, Pageable pageable)throws ParseException;
    DownloadFile readFile(Long id);
}
