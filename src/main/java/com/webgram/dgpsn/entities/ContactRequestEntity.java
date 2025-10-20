package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeDemande;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "contact_request")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactRequestEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "crq_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "crq_request_type")
    private TypeDemande requestType;

    @Column(name = "crq_last_name", length = 50)
    private String lastName;

    @Column(name = "crq_first_name", length = 50)
    private String firstName;

    @Column(name = "crq_email", length = 100)
    private String email;

    @Column(name = "crq_phone", length = 20)
    private String phone;

    @Column(name = "crq_organization", length = 100)
    private String organization;

    @Column(name = "crq_subject", length = 150)
    private String subject;

    @Column(name = "crq_message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "crq_statut")
    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "crq_linked_service")
    private DirectionEntity service;

    //Date de création
    @Column(name = "crq_date_creation")
    private Date dateCreation;

    @Column(name = "crq_file_path")
    private String filePath;

    @Column(name = "crq_consent")
    private boolean consent;
}