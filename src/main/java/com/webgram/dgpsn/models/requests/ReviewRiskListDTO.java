package com.webgram.dgpsn.models.requests;

import lombok.Data;
import com.webgram.dgpsn.entities.ReviewEntity;

import java.util.List;

@Data
public class ReviewRiskListDTO {
    ReviewEntity review;
    List<Long> risks;
}
