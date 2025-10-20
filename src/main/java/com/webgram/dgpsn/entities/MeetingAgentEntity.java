package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "meeting_agent")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingAgentEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "meetagen_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meetagen_linked_meeting")
    private MeetingEntity meeting;

    @ManyToOne
    @JoinColumn(name = "meetagen_linked_agent")
    private AgentEntity agent;
}
