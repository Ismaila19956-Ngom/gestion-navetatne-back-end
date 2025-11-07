package com.webgram.dgpsn.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDashboardDTO {
    private Long totalAgents;
    private Long totalAgentsEnConges;
    private Long totalAgentsParDirection;
}