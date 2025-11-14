package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import com.webgram.dgpsn.entities.enums.TypeProjet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TreeNodeDTO implements Serializable {
    private Long id;
    private String code;
    private String nomenclature;
    private String name;
    private TypeProjet type;

    // Champs supplémentaires pour les indicateurs
//    private Double targetValue;
//    private Double valueReched;
    private Date startDate;
    private Date endDate;

    // Champs supplémentaires pour les tâches
    private Integer trimestre;
    private Integer mois;
    private String semaines;
    private String commentaire;
    private String statut;

    // Budget et dépenses (pour les niveaux supérieurs)
    private Double budget;
    private Double depense;
    private Double tauxExecution;

    private List<StructureDTO> structuresResponsables;
    private List<StructureDTO> actorsInvolved;

    private List<TreeNodeDTO> children = new ArrayList<>();

    public TreeNodeDTO(Long id, String code, String nomenclature, String name, TypeProjet type, StructureDTO structuresResponsables, StructureDTO actorsInvolved) {
        this.id = id;
        this.code = code;
        this.nomenclature = nomenclature;
        this.name = name;
        this.type = type;
        this.children = new ArrayList<>();
        this.structuresResponsables = new ArrayList<>();
        this.actorsInvolved = new ArrayList<>();
        if (structuresResponsables != null) {
            this.structuresResponsables.add(structuresResponsables);
        }
        if (actorsInvolved != null) {
            this.actorsInvolved.add(actorsInvolved);
        }
    }

    public void addChild(TreeNodeDTO child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }
}
