package com.webgram.dgpsn.models.responses.ptba;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PtbaFundingSourcesDTO {
    // Map dynamique : clé = nom du bailleur (structure), valeur = montant
    @Builder.Default
    private Map<String, Double> sources = new HashMap<>();

    /**
     * Ajoute un montant pour un bailleur
     */
    public void addSource(String bailleurName, Double montant) {
        if (bailleurName != null && montant != null && montant > 0) {
            sources.put(bailleurName,
                    sources.getOrDefault(bailleurName, 0.0) + montant);
        }
    }

    /**
     * Retourne le total de toutes les sources
     */
    public Double getTotal() {
        return sources.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    /**
     * Retourne le montant pour un bailleur spécifique
     */
    public Double getSourceAmount(String bailleurName) {
        return sources.getOrDefault(bailleurName, 0.0);
    }

    /**
     * Retourne tous les noms de bailleurs
     */
    public Set<String> getBailleurNames() {
        return sources.keySet();
    }
}
