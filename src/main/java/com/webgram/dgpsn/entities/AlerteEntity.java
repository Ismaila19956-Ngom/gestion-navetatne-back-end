package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Priority;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "alerte")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlerteEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ale_id")
    private Long id;

    @Column(name = "ale_message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "ale_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "ale_read")
    private boolean read;

    @Column(name = "temp_priorite")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "ale_linked_user")
    private UserEntity user;

}
