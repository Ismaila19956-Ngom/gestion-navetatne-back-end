package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.WorkflowType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "workflow")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_id")
    private Long id;

    @Column(name = "work_code")
    private String code;

    @Column(name = "work_libelle")
    private String libelle;

    @Column(name = "work_type")
    @Enumerated(EnumType.STRING)
    private WorkflowType type;
}
