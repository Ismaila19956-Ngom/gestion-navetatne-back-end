package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "workflow_step")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStepEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wost_id")
    private Long id;

    @Column(name = "wost_code")
    private String code;

    @Column(name = "wost_libelle")
    private String libelle;

    @Column(name = "wost_ordre")
    private Integer ordre;

    @ManyToOne
    @JoinColumn(name = "wost_step_linked_workflow")
    private WorkflowEntity workflow;
}
