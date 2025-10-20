package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.ReviewDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;


public interface ReviewService {
    ReviewDTO createReview(MultipartFile file, String review) throws IOException;
    ReviewDTO updateReview(MultipartFile file, ReviewDTO reviewDTO) throws IOException;
    ReviewDTO readReview(Long id);
    void deleteReview(Long id);
    Page<ReviewDTO> readAllReview(Pageable pageable, Long projetId, String keyPoint, String date) throws ParseException;
    DownloadFile readFile(Long id);
    void importReview(MultipartFile file, Long porjectId);
    void exportReview(PrintWriter writer);
}
