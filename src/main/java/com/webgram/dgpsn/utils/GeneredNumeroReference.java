package com.webgram.dgpsn.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class GeneredNumeroReference {

//      private final NumeroReferenceRepository numeroReferenceRepository;
//
//    public void setCongeReferences(CongeDTO congeDTO) {
//        Map<ReferenceNumero, String> references = updateCongeReferences(congeDTO);
//        if (TypeConge.ADMINISTRATIF.equals(congeDTO.getTypeConge())) {
//            congeDTO.setNumeroDecision(references.get(ReferenceNumero.DECISION_CONGE));
//            congeDTO.setNumeroFiche(references.get(ReferenceNumero.FICHE_CONGE));
//            congeDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDOREAU_CONGE));
//            congeDTO.setNumeroDemande(references.get(ReferenceNumero.DEMANDE_CONGE));
//            congeDTO.setNumeroLettreTrans(references.get(ReferenceNumero.LETTRE_TRANSMISSION_CONGE));
//        } else if (TypeConge.MATERNITE.equals(congeDTO.getTypeConge())) {
//            congeDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDOREAU_CONGE_MATERNITE));
//            congeDTO.setNumeroDecision(references.get(ReferenceNumero.DECISION_CONGE_MATERNITE));
//            congeDTO.setNumeroDemande(references.get(ReferenceNumero.DEMANDE_CONGE_MATERNITE));
//            congeDTO.setNumeroFicheCirculation(references.get(ReferenceNumero.CIRCULATION_CONGE_MATERNITE));
//        } else if (TypeConge.MALADIE.equals(congeDTO.getTypeConge())) {
//            congeDTO.setNumeroDecision(references.get(ReferenceNumero.DECISION_CONGE_MALADIE));
////            congeDTO.setNumeroFiche(references.get(ReferenceNumero.FICHE_CONGE));
//            congeDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDOREAU_CONGE_MALADIE));
//            congeDTO.setNumeroDemande(references.get(ReferenceNumero.DEMANDE_CONGE_MALADIE));
//        } else {
//            congeDTO.setNumeroDecision(references.get(ReferenceNumero.DECISION_CONGE_LONG_DUREE));
//            congeDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDOREAU_CONGE_LONG_DUREE));
//            congeDTO.setNumeroDemande(references.get(ReferenceNumero.DEMANDE_CONGE_LONG_DUREE));
//        }
//    }

//    public void setAvancementReferences(AvancementDTO avancementDTO) {
//        Map<ReferenceNumero, String> references = updateAvancementReferences(avancementDTO);
//        if (TypeAvancement.NOMINATION.equals(avancementDTO.getTypeAvancement())) {
//            avancementDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_NOMINATION));
//            avancementDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_NOMINATION));
//            avancementDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_NOMINATION));
//            avancementDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_NOMINATION));
//        } else if (TypeAvancement.TITULARISATION.equals(avancementDTO.getTypeAvancement())) {
//            avancementDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_TITULARISATION));
//            avancementDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_TITULARISATION));
//            avancementDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_TITULARISATION));
//            avancementDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_TITULARISATION));
//        } else if (TypeAvancement.ECHELON.equals(avancementDTO.getTypeAvancement())) {
//            avancementDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_ECHELON));
//            avancementDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_ECHELON));
//            avancementDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_ECHELON));
//            avancementDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_ECHELON));
//        } else {
//            avancementDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_GRADE));
//            avancementDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_GRADE));
//            avancementDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_GRADE));
//            avancementDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_GRADE));
//        }
//    }
//


//        public void setSortieReferences(SortieDTO sortieDTO) {
//        Map<ReferenceNumero, String> references = updateSortieReferences(sortieDTO);
//        if (TypeSortie.DISPONIBILITE.equals(sortieDTO.getTypeSortie())) {
//            sortieDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_SORTIE_DISPONIBILITE));
//            sortieDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_SORTIE_DISPONIBILITE));
//            sortieDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_SORTIE_DISPONIBILITE));
//            sortieDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DISPONIBILITE));
//        } else if (TypeSortie.DETACHEMENT.equals(sortieDTO.getTypeSortie())) {
//            sortieDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_SORTIE_DETACHEMENT));
//            sortieDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DETACHEMENT));
//            sortieDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_SORTIE_DETACHEMENT));
//            sortieDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_SORTIE_CESSATION_TEMPORAIRe));
//        } else if (TypeSortie.STAGE.equals(sortieDTO.getTypeSortie())) {
//            sortieDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_SORTIE_MISE_EN_POSITION));
//            sortieDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSIONSORTIE_MISE_EN_POSITION));
//            sortieDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_SORTIE_MISE_EN_POSITION));
//            sortieDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_SORTIE_MISE_EN_POSITION));
//        }
//        else if (TypeSortie.CESSATION.equals(sortieDTO.getTypeSortie())) {
//            sortieDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_SORTIE_CESSATION_TEMPORAIRe));
//            sortieDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_CESSATION_TEMPORAIRe));
//            sortieDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_SORTIE_CESSATION_TEMPORAIRe));
//            sortieDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_SORTIE_CESSATION_TEMPORAIRe));
//        }
//        else if (TypeSortie.DECES.equals(sortieDTO.getTypeSortie())) {
//            sortieDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_SORTIE_DEFINITIVE_DECE));
//            sortieDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DEFINITIVE_DECE));
//            sortieDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_SORTIE_DEFINITIVE_DECE));
//            sortieDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_SORTIE_DEFINITIVE_DECE));
//        }
//        else if (TypeSortie.DEMISSION.equals(sortieDTO.getTypeSortie())) {
//            sortieDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_SORTIE_DEFINITIVE_DEMISSION));
//            sortieDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DEFINITIVE_DEMISSION));
//            sortieDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_SORTIE_DEFINITIVE_DEMISSION));
//            sortieDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_SORTIE_DEFINITIVE_DEMISSION));
//        }
//        else if (TypeSortie.RETRAITE.equals(sortieDTO.getTypeSortie())) {
//            sortieDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_SORTIE_DEFINITIVE_RETRAITE));
//            sortieDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DEFINITIVE_RETRAITE));
//            sortieDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_SORTIE_DEFINITIVE_RETRAITE));
//            sortieDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_SORTIE_DEFINITIVE_RETRAITE));
//        }
//        else {
//            sortieDTO.setNumeroBordereau(references.get(ReferenceNumero.BORDEREAU_SORTIE_DEFINITIVE_RADIATION));
//            sortieDTO.setNumeroLettre(references.get(ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DEFINITIVE_RADIATION));
//            sortieDTO.setNumeroArreter(references.get(ReferenceNumero.PROJET_ARRETE_SORTIE_DEFINITIVE_RADIATION));
//            sortieDTO.setNumeroDecret(references.get(ReferenceNumero.PROJET_DECRET_SORTIE_DEFINITIVE_RADIATION));
//        }
//    }


//    public void setAffectationReferences(AffectationDTO affectationDTO) {
//        Map<ReferenceNumero, String> references = updateAffectationReferences(affectationDTO);
//        affectationDTO.setNumeroNoteService(references.get(ReferenceNumero.NOTE_SERVICE_AFFECTATION));
//    }
//
//    public void setImpuatationBudgetaireReferences(ImputationBudgetaireDTO imputationBudgetaireDTO) {
//        Map<ReferenceNumero, String> references = updateImputationReferences(imputationBudgetaireDTO);
//        imputationBudgetaireDTO.setNumeroImputation(references.get(ReferenceNumero.IMPUTATION_BUDGETAIRE));
//    }

//    public Map<ReferenceNumero, String> updateCongeReferences(CongeDTO congeDTO) {
//        Map<ReferenceNumero, String> references = new HashMap<>();
//        if (TypeConge.ADMINISTRATIF.equals(congeDTO.getTypeConge())) {
//            Stream.of(ReferenceNumero.DECISION_CONGE, ReferenceNumero.FICHE_CONGE, ReferenceNumero.DEMANDE_CONGE, ReferenceNumero.BORDOREAU_CONGE, ReferenceNumero.LETTRE_TRANSMISSION_CONGE)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.CONGE_ADMINISTRATIF)
//                    .forEach(referenceNumero -> {
//                        NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                        references.put(referenceNumero, updatedRef.getNumeroActuel());
//                    });
//        } else if (TypeConge.MATERNITE.equals(congeDTO.getTypeConge())) {
//            Stream.of(ReferenceNumero.BORDOREAU_CONGE_MATERNITE, ReferenceNumero.DECISION_CONGE_MATERNITE, ReferenceNumero.DEMANDE_CONGE_MATERNITE, ReferenceNumero.CIRCULATION_CONGE_MATERNITE)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.CONGE_MATERNITE)
//                    .forEach(referenceNumero -> {
//                NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                references.put(referenceNumero, updatedRef.getNumeroActuel());
//            });
//        } else if (TypeConge.MALADIE.equals(congeDTO.getTypeConge())) {
//            Stream.of(ReferenceNumero.DECISION_CONGE_MALADIE, ReferenceNumero.DEMANDE_CONGE_MALADIE, ReferenceNumero.BORDOREAU_CONGE_MALADIE)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.CONGE_MALADIE)
//                    .forEach(referenceNumero -> {
//                NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                references.put(referenceNumero, updatedRef.getNumeroActuel());
//            });
//        } else {
//            Stream.of(ReferenceNumero.DECISION_CONGE_LONG_DUREE, ReferenceNumero.DEMANDE_CONGE_LONG_DUREE, ReferenceNumero.BORDOREAU_CONGE_LONG_DUREE).filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.CONGE_LONGUE_DUREE).forEach(referenceNumero -> {
//                NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                references.put(referenceNumero, updatedRef.getNumeroActuel());
//            });
//        }
//        return references;
//    }


//    public Map<ReferenceNumero, String> updateAvancementReferences(AvancementDTO avancementDTO) {
//        Map<ReferenceNumero, String> references = new HashMap<>();
//        if (TypeAvancement.NOMINATION.equals(avancementDTO.getTypeAvancement())) {
//            Stream.of(ReferenceNumero.BORDEREAU_NOMINATION, ReferenceNumero.PROJET_ARRETE_NOMINATION, ReferenceNumero.PROJET_DECRET_NOMINATION, ReferenceNumero.LETTRE_TRANSMISSION_NOMINATION)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.NOMINATION_AVANCEMENT)
//                    .forEach(referenceNumero -> {
//                        NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                        references.put(referenceNumero, updatedRef.getNumeroActuel());
//                    });
//        } else if(TypeAvancement.TITULARISATION.equals(avancementDTO.getTypeAvancement())) {
//            Stream.of(ReferenceNumero.BORDEREAU_TITULARISATION, ReferenceNumero.PROJET_ARRETE_TITULARISATION, ReferenceNumero.PROJET_DECRET_TITULARISATION,ReferenceNumero.LETTRE_TRANSMISSION_TITULARISATION)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.TITULARISATION_AVANCEMENT)
//                    .forEach(referenceNumero -> {
//                NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                references.put(referenceNumero, updatedRef.getNumeroActuel());
//            });
//        } else if (TypeAvancement.ECHELON.equals(avancementDTO.getTypeAvancement())) {
//            Stream.of(ReferenceNumero.BORDEREAU_ECHELON, ReferenceNumero.PROJET_ARRETE_ECHELON, ReferenceNumero.PROJET_DECRET_ECHELON,ReferenceNumero.LETTRE_TRANSMISSION_ECHELON)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.ECHELON_AVANCEMENT)
//                    .forEach(referenceNumero -> {
//                NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                references.put(referenceNumero, updatedRef.getNumeroActuel());
//            });
//        } else {
//            Stream.of(ReferenceNumero.BORDEREAU_GRADE, ReferenceNumero.PROJET_ARRETE_GRADE, ReferenceNumero.PROJET_DECRET_GRADE,ReferenceNumero.LETTRE_TRANSMISSION_GRADE)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.GRADE_AVANCEMENT)
//                    .forEach(referenceNumero -> {
//                NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                references.put(referenceNumero, updatedRef.getNumeroActuel());
//            });
//        }
//        return references;
//    }
//

//    public Map<ReferenceNumero, String> updateSortieReferences(SortieDTO sortieDTO) {
//        Map<ReferenceNumero, String> references = new HashMap<>();
//        if (TypeSortie.DISPONIBILITE.equals(sortieDTO.getTypeSortie())) {
//            Stream.of(ReferenceNumero.BORDEREAU_SORTIE_DISPONIBILITE, ReferenceNumero.PROJET_ARRETE_SORTIE_DISPONIBILITE, ReferenceNumero.PROJET_DECRET_SORTIE_DISPONIBILITE, ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DISPONIBILITE)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.SORTIE_DISPONIBILITE)
//                    .forEach(referenceNumero -> {
//                        NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                        references.put(referenceNumero, updatedRef.getNumeroActuel());
//                    });
//        } else if(TypeSortie.DETACHEMENT.equals(sortieDTO.getTypeSortie())) {
//            Stream.of(ReferenceNumero.BORDEREAU_SORTIE_DETACHEMENT, ReferenceNumero.PROJET_ARRETE_SORTIE_DETACHEMENT, ReferenceNumero.PROJET_DECRET_SORTIE_DETACHEMENT,ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DETACHEMENT)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.SORTIE_DETACHEMENT)
//                    .forEach(referenceNumero -> {
//                NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                references.put(referenceNumero, updatedRef.getNumeroActuel());
//            });
//         } else if (TypeSortie.CESSATION.equals(sortieDTO.getTypeSortie())) {
//            Stream.of(ReferenceNumero.BORDEREAU_SORTIE_CESSATION_TEMPORAIRe, ReferenceNumero.PROJET_ARRETE_SORTIE_CESSATION_TEMPORAIRe, ReferenceNumero.PROJET_DECRET_SORTIE_CESSATION_TEMPORAIRe, ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_CESSATION_TEMPORAIRe)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.SORTIE_CESSATION_TEMPORAIRe)
//                    .forEach(referenceNumero -> {
//                        NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                        references.put(referenceNumero, updatedRef.getNumeroActuel());
//                    });
//          }else if (TypeSortie.STAGE.equals(sortieDTO.getTypeSortie())) {
//                Stream.of(ReferenceNumero.BORDEREAU_SORTIE_MISE_EN_POSITION, ReferenceNumero.PROJET_ARRETE_SORTIE_MISE_EN_POSITION, ReferenceNumero.PROJET_DECRET_SORTIE_MISE_EN_POSITION,ReferenceNumero.LETTRE_TRANSMISSIONSORTIE_MISE_EN_POSITION)
//                        .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.SORTIE_MISE_EN_POSITION)
//                        .forEach(referenceNumero -> {
//                            NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                            references.put(referenceNumero, updatedRef.getNumeroActuel());
//                        });
//         }
//        else if (TypeSortie.DEMISSION.equals(sortieDTO.getTypeSortie())) {
//            Stream.of(ReferenceNumero.BORDEREAU_SORTIE_DEFINITIVE_DEMISSION, ReferenceNumero.PROJET_ARRETE_SORTIE_DEFINITIVE_DEMISSION, ReferenceNumero.PROJET_DECRET_SORTIE_DEFINITIVE_DEMISSION,ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DEFINITIVE_DEMISSION)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.SORTIE_DEFINITIVE_DEMISSION)
//                    .forEach(referenceNumero -> {
//                        NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                        references.put(referenceNumero, updatedRef.getNumeroActuel());
//                    });
//        }
//        else if (TypeSortie.RETRAITE.equals(sortieDTO.getTypeSortie())) {
//            Stream.of(ReferenceNumero.BORDEREAU_SORTIE_DEFINITIVE_RETRAITE, ReferenceNumero.PROJET_ARRETE_SORTIE_DEFINITIVE_RETRAITE, ReferenceNumero.PROJET_DECRET_SORTIE_DEFINITIVE_RETRAITE,ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DEFINITIVE_RETRAITE)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.SORTIE_DEFINITIVE_RETRAITE)
//                    .forEach(referenceNumero -> {
//                        NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                        references.put(referenceNumero, updatedRef.getNumeroActuel());
//                    });
//        }
//        else if (TypeSortie.DECES.equals(sortieDTO.getTypeSortie())) {
//            Stream.of(ReferenceNumero.BORDEREAU_SORTIE_DEFINITIVE_DECE, ReferenceNumero.PROJET_ARRETE_SORTIE_DEFINITIVE_DECE, ReferenceNumero.PROJET_DECRET_SORTIE_DEFINITIVE_DECE,ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DEFINITIVE_DECE)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.SORTIE_DEFINITIVE_DECE)
//                    .forEach(referenceNumero -> {
//                        NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                        references.put(referenceNumero, updatedRef.getNumeroActuel());
//                    });
//         }
//        else {
//            Stream.of(ReferenceNumero.BORDEREAU_SORTIE_DEFINITIVE_RADIATION, ReferenceNumero.PROJET_ARRETE_SORTIE_DEFINITIVE_RADIATION, ReferenceNumero.PROJET_DECRET_SORTIE_DEFINITIVE_RADIATION,ReferenceNumero.LETTRE_TRANSMISSION_SORTIE_DEFINITIVE_RADIATION)
//                    .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.SORTIE_DEFINITIVE_RADIATION)
//                    .forEach(referenceNumero -> {
//                NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                references.put(referenceNumero, updatedRef.getNumeroActuel());
//            });
//        }
//        return references;
//    }

//    public Map<ReferenceNumero, String> updateAffectationReferences(AffectationDTO affectationDTO) {
//        Map<ReferenceNumero, String> references = new HashMap<>();
//        Stream.of(ReferenceNumero.NOTE_SERVICE_AFFECTATION)
//                .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.NOTE_SERVICE_AFFECTATION)
//                .forEach(referenceNumero -> {
//                    NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                    references.put(referenceNumero, updatedRef.getNumeroActuel());
//                });
//        return references;
//    }
//
//    public Map<ReferenceNumero, String> updateImputationReferences(ImputationBudgetaireDTO imputationBudgetaireDTO) {
//        Map<ReferenceNumero, String> references = new HashMap<>();
//        Stream.of(ReferenceNumero.IMPUTATION_BUDGETAIRE)
//                .filter(ref -> ref.getTypeReferenceNumero() == TypeReferenceNumero.IMPUTATION_BUDGETAIRE)
//                .forEach(referenceNumero -> {
//                    NumeroReferenceEntity updatedRef = updateNumeroReference(referenceNumero);
//                    references.put(referenceNumero, updatedRef.getNumeroActuel());
//                });
//        return references;
//    }

//    public NumeroReferenceEntity updateNumeroReference(ReferenceNumero referenceNumero) {
//        NumeroReferenceEntity numeroReference = numeroReferenceRepository.findByReferenceNumero(referenceNumero).orElseThrow(() -> new ResourceNotFoundException("NumeroReference not found for: " + referenceNumero));
//        String nouveauNumero = generateNextNumero(numeroReference.getNumeroActuel());
//        numeroReference.setNumeroActuel(nouveauNumero);
//        return numeroReferenceRepository.save(numeroReference);
//    }

    private String generateNextNumero(String numeroActuel) {
        if (numeroActuel == null || numeroActuel.isEmpty()) {
            return "0001";
        }
        try {
            String cleanNumero = numeroActuel.trim().replaceAll("[\\n\\r\\s]+", "");
            int numero = Integer.parseInt(cleanNumero);
            numero++;
            return String.format("%04d", numero);
        } catch (NumberFormatException e) {
            log.error("Error parsing numero: {}", numeroActuel, e);
            return "0001";
        }
    }

}
