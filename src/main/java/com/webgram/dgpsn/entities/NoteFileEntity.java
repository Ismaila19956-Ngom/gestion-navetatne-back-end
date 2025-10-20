package com.webgram.dgpsn.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "note_file")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NoteFileEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pass_id")
    private Long id;

    @JoinColumn(name = "note")
    private Double note;


    @JsonIgnoreProperties("NoteFileEntity")
    @ManyToOne
    @JoinColumn(name = "note_file_linked_market_file")
    private MarketFileEntity marketFileEntity;

    @ManyToOne
    @JoinColumn(name = "note_linked_passation_market_critere")
    private PassationMarketCritereEntity passationMarketCritere;








}
