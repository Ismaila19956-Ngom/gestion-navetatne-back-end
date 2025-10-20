package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.AgentBafDTO;

import java.util.Map;

public interface AgentBafService {
    AgentBafDTO create(AgentBafDTO agentBafDTO);
    AgentBafDTO update(AgentBafDTO agentBafDTO);
    AgentBafDTO read(Long agentBafId);
    void delete(Long agentBafId);
    Page<AgentBafDTO> readAllAgentBafs(Map<String, String> searchParams, Pageable pageable);
}