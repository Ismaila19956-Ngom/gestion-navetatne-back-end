package com.webgram.dgpsn.models.requests;

import lombok.Data;
import com.webgram.dgpsn.entities.ReviewEntity;
import com.webgram.dgpsn.entities.RiskEntity;

@Data
public class ReviewRiskDTO {
    ReviewEntity review;
    RiskEntity risk;
}
