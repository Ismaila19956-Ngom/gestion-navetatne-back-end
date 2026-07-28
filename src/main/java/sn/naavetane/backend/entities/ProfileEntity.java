package sn.naavetane.backend.entities;

import sn.naavetane.backend.entities.enums.Portee;
import lombok.*;
import sn.naavetane.backend.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "profile")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProfileEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prf_id")
    private Long id;

    @Column(name = "prf_code")
    private String code;

    @Column(name = "prf_libelle")
    private String libelle;

    @Column(name = "prf_portee")
    @Enumerated(EnumType.STRING)
    private Portee portee;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @JoinTable(name = "profile_permission", joinColumns = @JoinColumn(name = "prf_id"))
    @Column(name = "permission", nullable = false)
    Collection<String> permissions;
}
