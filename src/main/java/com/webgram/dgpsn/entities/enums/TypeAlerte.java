package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public enum TypeAlerte {
    CREATION_EVALUATION_ENVIRONNEMENTAL("Création evaluation Environmental",CategorieAlerte.EVALUATION_ENVIRONNEMENTAL, List.of(Tag.TITRE_PROJET,Tag.TYPE_PROJET,Tag.DATE,Tag.REFERENCE)),
//    FORMULAIRE_GENERAL("Creation  demande",CategorieAlerte.FORMULAIRE_GENERAL, List.of(Tag.TYPE_DEMANDE,Tag.LiBELLE,Tag.DATE)),
    POLLUTION_REJET(" Creation Rejet d’Eau Usée ",CategorieAlerte.GESTION_POLLUTION,List.of(Tag.DATE_PRELEVEMENT,Tag.LiBELLE)),
    GESTION_QUALITE(" Creation  qualite des Milieux",CategorieAlerte.GESTION_POLLUTION,List.of(Tag.DATE_PRELEVEMENT,Tag.LiBELLE)),
    GESTION_PRODUIT(" Creation produit chimique ",CategorieAlerte.GESTION_POLLUTION,List.of(Tag.DATE_TRANSPORT,Tag.ORIGINE,Tag.DESTINATION,Tag.LiBELLE)),
    GESTION_DECHET(" Creation  dechets dangereux ",CategorieAlerte.GESTION_POLLUTION,List.of(Tag.DATE_TRANSPORT,Tag.ORIGINE,Tag.DESTINATION,Tag.LiBELLE)),
    GESTION_PLASTIQUE(" Creation  produit plastique ",CategorieAlerte.GESTION_POLLUTION,List.of(Tag.DATE_MOUVEMENT,Tag.ORIGINE,Tag.DESTINATION,Tag.LiBELLE));
//    QUALITE_AIR(" Creation  mesure qualite air",CategorieAlerte.QUALITE_AIR,List.of(Tag.DATE_MESURE,Tag.LiBELLE,Tag.POLLUANT_PRINCIPAL)),
//    INSPECTION_ICPE(" Creation inspection ",CategorieAlerte.INSPECTION_ICPE,List.of(Tag.DATE_INSPECTION,Tag.REFERENCE_INSPECTION)),
//    URGENCE_ENVIRONNEMENTAL(" Creation declaration ",CategorieAlerte.URGENCE_ENVIRONNEMENTAL,List.of(Tag.REFERENCE,Tag.DATE_RECEPTION,Tag.AGENT)),
//    DIRECTION_REGIONAL(" Creation  fiche declaration ",CategorieAlerte.DIRECTION_REGIONAL,List.of(Tag.LiBELLE,Tag.NUMERO_RCCM,Tag.DATE)),
//    BUDGET_ACTIVITE("Creation  Budget ",CategorieAlerte.PROJET,List.of(Tag.LiBELLE,Tag.DATE_DEBUT,Tag.DATE_FIN,Tag.ANNEE)),
//    DEPENSE_ACTIVITE("Creation  depense ",CategorieAlerte.PROJET,List.of(Tag.LiBELLE,Tag.DATE_DEBUT,Tag.DATE_FIN,Tag.ANNEE)),
//    EVALUATION_STARTUP(" Nouvelle Evaluation ",CategorieAlerte. EVALUATION_STARTUP,List.of(Tag.LiBELLE,Tag.DATE_DEBUT,Tag.DATE_FIN,Tag.ANNEE));


    @Getter
    @Setter
    String description;
    @Getter
    @Setter
    CategorieAlerte categorieAlerte;
    @Getter
    @Setter List<Tag> tags;

    TypeAlerte(String description, CategorieAlerte categorieAlerte, List<Tag> tags) {
        this.description = description;
        this.tags = tags;
        this.categorieAlerte = categorieAlerte;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeAlerte fromValue(Object typeAlerte) {
        if (typeAlerte instanceof Map) {
            Map<String, Object> mapTypeAlerte = (Map<String, Object>) typeAlerte;
            if (mapTypeAlerte.containsKey("name")) {
                return TypeAlerte.valueOf(mapTypeAlerte.get("name").toString());
            }
        }
        if (typeAlerte instanceof String) {
            return TypeAlerte.valueOf(typeAlerte.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", TypeAlerte.class, typeAlerte, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description,
                "categorieAlerte", categorieAlerte,
                "tags", tags
        );
    }

    public static Set<TypeAlerte> getAllTypesAlerte() {
        return Arrays.stream(values())
//                .filter(typeAlertes -> typeAlertes.categorieAlerte.equals(categorieAlerte))
                .collect(Collectors.toSet());
    }

    public static Set<TypeAlerte> getAllTypesAlerte(CategorieAlerte categorieAlerte) {
        return Arrays.stream(values())
                .filter(typeAlertes -> typeAlertes.categorieAlerte.equals(categorieAlerte))
                .collect(Collectors.toSet());
    }
}
