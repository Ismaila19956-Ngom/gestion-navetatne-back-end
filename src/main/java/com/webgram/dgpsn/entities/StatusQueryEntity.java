package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "status_query")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusQueryEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stqr_id")
    private Long id;

    @Column(name = "stqr_libelle")
    private String libelle;

    @Column(name = "stqr_responsable")
    private String responsable;

    @Column(name = "stqr_identification_date")
    @Temporal(TemporalType.DATE)
    private Date identificationDate;

    @Column(name = "stqr_deadline_date")
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Column(name = "stqr_comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "stqr_linked_query")
    private QueryEntity query;




}
