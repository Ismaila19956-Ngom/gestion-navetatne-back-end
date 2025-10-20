package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "fol_name")
    private String name;

    @Column(name = "fol_parent")
    private Long parentId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subFolder",
            joinColumns = @JoinColumn(name="parent_Id", referencedColumnName="fol_id"),
            inverseJoinColumns = @JoinColumn(name="child_id", referencedColumnName="fol_id", unique = true))
    private Set<FolderEntity> subFolders = new HashSet<>();
}
