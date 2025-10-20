package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.CategorieAlerte;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Table(name = "template")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "temp_id")
    private Long id;

    @Column(name = "temp_libelle")
    private String libelle;

    @Column(name = "temp_type_alerte", unique = true)
    @Enumerated(EnumType.STRING)
    private TypeAlerte typeAlerte;

    @Column(name = "temp_priorite")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "temp_delais")
    private Long deadlines;

    @Column(name = "temp_categorie_alerte")
    @Enumerated(EnumType.STRING)
    private CategorieAlerte categorieAlerte;

    @Column(name = "temp_send_mail")
    private boolean sendMail;

    @Column(name = "temp_send_sms")
    private boolean sendSms;

    @Column(name = "temp_message", columnDefinition = "TEXT")
    private String message;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "template_profile", joinColumns = @JoinColumn(name = "temp_id"),
    inverseJoinColumns = @JoinColumn(name = "prf_id"))
    private Set<ProfileEntity> profiles = new HashSet<>();


}
