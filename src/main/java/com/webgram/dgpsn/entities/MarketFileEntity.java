package com.webgram.dgpsn.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "market_file")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MarketFileEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mf_id")
    private Long id;

    @Column(name = "file_number", unique=false)
    private String fileNumber;

    @Column(name = "bidder_name", unique=false)
    private String bidderName;

    @Column(name = "legal_representative", unique=false)
    private String legalRepresentative;

    @Column(name = "reception_manager", unique=false)
    private String receptionManager;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "date_recept_file")
    @Temporal(TemporalType.DATE)
    private Date dateReceptFile;

    @Temporal(TemporalType.TIME)
    private Date timeReceptFile;

    @ManyToOne
    @JoinColumn(name = "reception_status")
    private LabelEntity receptionStatus;

    @ManyToOne
    @JoinColumn(name = "market_file_linked_passation_market")
    private PassationMarketEntity passationMarket;

    @Column(name = "market_file_description", columnDefinition = "TEXT")
    private String description;


    @JoinColumn(name = "note_final")
    private double noteFinal;

//    @JsonIgnore
    //@JsonIgnoreProperties("marketFileEntity")

//@JsonBackReference
@JsonIgnoreProperties("marketFileEntity")
    @OneToMany(mappedBy = "marketFileEntity",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private List<NoteFileEntity> noteFileEntities;



}
