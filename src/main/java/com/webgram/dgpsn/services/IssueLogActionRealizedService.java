package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.IssueLogActionRealizedDTO;

import java.util.List;

public interface IssueLogActionRealizedService {
    IssueLogActionRealizedDTO create(IssueLogActionRealizedDTO issueLogActionRealizedDTO);
    IssueLogActionRealizedDTO update(IssueLogActionRealizedDTO issueLogActionRealizedDTO);
    IssueLogActionRealizedDTO read(Long issueLogActionRealizedId);
    List<IssueLogActionRealizedDTO> readByIssueLog(Long issuelogId);
    void delete(Long IssueLogActionRealizedId);
}
