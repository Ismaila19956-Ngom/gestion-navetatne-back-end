package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "direction")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectionEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dir_id")
    private Long id;

    @Column(name = "dir_code", unique = true)
    private String code;

    @Column(name = "dir_libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "dir_parent")
    private DirectionEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DirectionEntity> children = new ArrayList<>();

    public void addChildren(List<DirectionEntity> children) {
        if(Objects.isNull(this.children)) {
            this.children = new ArrayList<>();
        }
        children.forEach(child -> child.setParent(this));
        this.children.addAll(children);
    }
}
