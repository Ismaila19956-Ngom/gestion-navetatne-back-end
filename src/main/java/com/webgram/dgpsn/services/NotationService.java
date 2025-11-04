package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.NotationDTO;

import java.util.List;

public interface NotationService {

    /**
     * Crée une nouvelle notation pour un candidat.
     */
    NotationDTO createNotation(NotationDTO dto);

    /**
     * Met à jour une notation existante.
     */
    NotationDTO updateNotation(Long id, NotationDTO dto);

    /**
     * Supprime une notation par son ID.
     */
    void deleteNotation(Long id);

    /**
     * Récupère une notation par son ID.
     */
    NotationDTO getNotationById(Long id);

    /**
     * Récupère toutes les notations d’un candidat.
     */
    List<NotationDTO> getNotationsByCandidat(Long candidatId);

    /**
     * Liste toutes les notations.
     */
    List<NotationDTO> getAllNotations();
}
