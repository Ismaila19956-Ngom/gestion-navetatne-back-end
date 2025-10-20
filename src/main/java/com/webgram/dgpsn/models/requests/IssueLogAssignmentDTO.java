package com.webgram.dgpsn.models.requests;

import lombok.Data;
import com.webgram.dgpsn.models.IssueLogDTO;

@Data
public class IssueLogAssignmentDTO {
    Long assignmentId;
    IssueLogDTO issueLog;
}
