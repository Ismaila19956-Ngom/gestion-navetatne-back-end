package com.webgram.dgpsn.models.requests;

import lombok.Data;
import com.webgram.dgpsn.models.AssignmentDTO;

import java.util.List;

@Data
public class IssueLogAssignmentListDTO {
    AssignmentDTO assignment;
    List<Long> issueLogs;
}
