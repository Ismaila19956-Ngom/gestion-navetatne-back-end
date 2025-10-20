package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.AgentDTO;

import java.util.List;

public interface MeetingAgentService {
    List<AgentDTO>readAgentsByMeeting(Long meetingId);
}
