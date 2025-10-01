package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "folder")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fol_id")
    private Long id;

    @Column(name = "fol_code")
    private String code;

    @Column(name = "fol_libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "fol_parent")
    private FolderEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FolderEntity> children = new ArrayList<>();
}
