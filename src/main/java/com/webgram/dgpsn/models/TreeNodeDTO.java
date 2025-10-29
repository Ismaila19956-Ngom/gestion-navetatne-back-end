package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import com.webgram.dgpsn.entities.enums.TypeProjet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
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
    private Double budget = 0.0;
    private Double depense = 0.0;
    private Double tauxExecution;
    private List<TreeNodeDTO> children = new ArrayList<>();

    public TreeNodeDTO(Long id, String code, String nomenclature, String name, TypeProjet type) {
        this.id = id;
        this.code = code;
        this.nomenclature = nomenclature;
        this.name = name;
        this.type = type;
    }

    public void addChild(TreeNodeDTO child) {
        this.children.add(child);
    }
}
