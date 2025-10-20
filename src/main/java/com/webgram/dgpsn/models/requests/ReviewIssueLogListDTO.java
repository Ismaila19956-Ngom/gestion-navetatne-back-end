package com.webgram.dgpsn.models.requests;

import lombok.Data;
import com.webgram.dgpsn.models.ReviewDTO;

import java.util.List;

@Data
public class ReviewIssueLogListDTO {
    Long id;
    ReviewDTO review;
    List<Long> issueLogs;
}
