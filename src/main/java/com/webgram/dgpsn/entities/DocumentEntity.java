package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.CategoryDocument;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "doument")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DocumentEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "doc_code")
    private String code;

    @Column(name = "doc_libelle")
    private String libelle;

    @Column(name = "doc_path")
    private String path;

    @Column(name = "doc_author")
    private String author;

    @Column(name = "doc_comment")
    private String comment;
    private Long categoryId;
    @Column(name = "doc_category")
    @Enumerated(EnumType.STRING)
    private CategoryDocument category;
    @ManyToOne
    @JoinColumn(name = "doc_linked_folder")
    private FolderEntity folder;
}