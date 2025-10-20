package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.StatusType;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "status")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sta_id")
    private Long id;

    @Column(name = "sta_code", unique=true)
    private String code;

    @Column(name = "sta_libelle")
    private String libelle;

    @Column(name = "sta_linked_status_type")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;
}
