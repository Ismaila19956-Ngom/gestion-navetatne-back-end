package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "autre_document")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AutreDocumentEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "aut_id")
    private Long id;

    @Column(name = "aut_doc_id")
    private Long documentId;
    @Column(name = "aut_agent_id")
    private Long agentId;

}
