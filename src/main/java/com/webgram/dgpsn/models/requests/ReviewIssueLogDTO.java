package com.webgram.dgpsn.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.webgram.dgpsn.models.IssueLogDTO;
import com.webgram.dgpsn.models.ReviewDTO;

@Data
public class ReviewIssueLogDTO {
    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    ReviewDTO review;
    IssueLogDTO issueLog;
    Long revewId;
}
