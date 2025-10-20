package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "preselectionned_file")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PreselectionnedFileEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mf_id")
    private Long id;


    @Column(name = "is_winner", nullable = false)
    private boolean winner=false;

    @ManyToOne
    @JoinColumn(name = "preselectionned_file_linked_market_file")
    private MarketFileEntity marketFileEntity;

    @ManyToOne
    @JoinColumn(name = "preselectionned_file_linked_passation_market")
    private PassationMarketEntity passationMarket;








}
