package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "workflow_step_validation_user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowStepValidationUserEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wsvau_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wsvau_linked_workflow_step_validation")
    private WorkflowStepValidationEntity workflowStepValidation;

    @ManyToOne
    @JoinColumn(name = "wsvau_linked_user")
    private UserEntity user;
}
