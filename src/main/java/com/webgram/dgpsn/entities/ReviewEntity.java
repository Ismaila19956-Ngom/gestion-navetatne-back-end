package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "review")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ReviewEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rvw_id")
    private Long id;

    @Column(name = "rvw_comment", columnDefinition = "TEXT")
    private String keyPoint;

    @Column(name = "rvw_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "rvw_path")
    private String path;


    @ManyToOne()
    @JoinColumn(name = "rvw_linked_projet")
    private ManagementUnitEntity projet;
}
