package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "document_public")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentPublicEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doc_pub_id")
    private Long id;

    @Column(name = "doc_pub_titre", columnDefinition = "TEXT")
    private String titre;

    @Column(name = "doc_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "doc_pub_author")
    private String authors;

    @Column(name = "doc_pub_themes")
    private String themes;

    @Column(name = "doc_pub_path")
    private String path;

    @Column(name = "doc_pub_publish")
    private boolean publish;

    @ManyToOne
    @JoinColumn(name = "doc_linked_document_type")
    private LabelEntity documentType;

    @ManyToOne
    @JoinColumn(name = "doc_linked_folder")
    private FolderEntity folder;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DOCUMENT_PAD",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "pad_id"))
    private Set<StructureEntity> partners = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DOCUMENT_SECTEUR",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "secteur_id"))
    private Set<SubSectorEntity> sectors = new HashSet<>();
}
