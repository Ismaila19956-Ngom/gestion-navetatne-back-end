package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.CongeEntity;
import com.webgram.dgpsn.entities.OrdreMissionEntity;
import com.webgram.dgpsn.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.Priority;

import java.util.Date;
import java.util.List;

public interface AlerteService {
  //  List<AlerteDTO> getAlertNotRead(long userId);
   Page<AlerteDTO> readAll(Pageable pageable, String message, Date date, String critere, Date endDate, Boolean read, Priority priority, String username);
    void newOrdreMission(OrdreMissionEntity ordreMission);
    void generateAlertCreateConge(CongeDTO congeDTO);
    void generateAlertCreateCessationService(CessationFonctionDTO cessationFonctionDTO);
    void generateAlerteForCongeAccepte(CongeEntity conge);
    void setAlertToRead(Long alertId);
 void generateAlertNouveauRecrutement(RecrutementDTO recrutementDTO);
 void generateAlertNouveauBudget(BudgetDgpsnDTO budgetDTO);
 void generateAlertUpdateBudget(BudgetDgpsnDTO budgetDTO);
 void generateAlertDeleteBudget(BudgetDgpsnDTO budgetDTO);
 void generateAlertNouvelleLigneBudget(LigneBudgetaireDTO ligneDTO);
 void generateAlertUpdateLigneBudget(LigneBudgetaireDTO ligneDTO);
 void generateAlertDeleteLigneBudget(LigneBudgetaireDTO ligneDTO);
 void generateAlertMultipleLignesBudgetAjoutees(List<LigneBudgetaireDTO> lignesDTOs, Long budgetId, double montantTotalAjoute);


}
