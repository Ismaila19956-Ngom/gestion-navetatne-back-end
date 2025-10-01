package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.WorkflowValidationType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "workflow_step_validation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStepValidationEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wsva_id")
    private Long id;

    @Column(name = "wsva_type_validation")
    @Enumerated(EnumType.STRING)
    private WorkflowValidationType validationType;

    @ManyToOne
    @JoinColumn(name = "wsva_linked_workflow_step")
    private WorkflowStepEntity workflowStep;
}
