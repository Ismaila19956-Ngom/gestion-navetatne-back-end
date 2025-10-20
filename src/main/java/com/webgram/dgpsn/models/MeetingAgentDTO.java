package com.webgram.dgpsn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingAgentDTO {
    MeetingDTO meeting;
    List<Long> agents;
    Set<AgentDTO> agentList;
}
