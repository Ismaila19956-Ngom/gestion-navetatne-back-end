package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.models.landingPage.KeysDataDTO;
import com.webgram.dgpsn.models.landingPage.ProjectFundingDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.LandingPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LandingPageServiceImpl implements LandingPageService {
    private final ManagementUnitRepository managementUnitRepository;
    private final FundingRepository fundingRepository;
    private final SubSectorRepository subSectorRepository;
    private final StructureRepository structureRepository;
    private final BudgetRepository budgetRepository;

//    @Transactional
//    @Override
//    public KeysDataDTO getKeysData() {
//        var data = new HashMap<String, Object>();
//        var totalProjects = managementUnitRepository.countTotalProjects()
//                .orElseThrow(()-> new RuntimeException("Aucun projet trouver"));
//        var totalFunding = fundingRepository.calculateTotalFunding()
//                .orElseThrow(()-> new RuntimeException("Aucun financement trouver"));
//        var totalSubSector = subSectorRepository.countTotalSubSector()
//                .orElseThrow(() -> new RuntimeException("Aucun sous secteur trouver"));
//        var totalPartner = structureRepository.countTotalPartner()
//                .orElseThrow(() -> new RuntimeException("Aucun partenaire trouver"));
//
//        data.put("totalProjects", totalProjects);
//        data.put("totalFunding", totalFunding);
//        data.put("totalSubSector", totalSubSector);
//        data.put("totalPartner", totalPartner);
//
//        return KeysDataDTO.builder()
//                .data(data)
//                .build();
//    }


    @Transactional
    @Override
    public KeysDataDTO getKeysData() {
        var data = new HashMap<String, Object>();
        var totalProjects = managementUnitRepository.countTotalProjects()
                .orElseThrow(() -> new RuntimeException("Aucun projet trouvé"));
        var totalFunding = budgetRepository.calculateTotalActualAmountForProjects(); // Changement ici
        var totalSubSector = subSectorRepository.countTotalSubSector()
                .orElseThrow(() -> new RuntimeException("Aucun sous-secteur trouvé"));
        var totalPartner = structureRepository.countTotalPartner()
                .orElseThrow(() -> new RuntimeException("Aucun partenaire trouvé"));

        data.put("totalProjects", totalProjects);
        data.put("totalFunding", totalFunding);
        data.put("totalSubSector", totalSubSector);
        data.put("totalPartner", totalPartner);

        return KeysDataDTO.builder()
                .data(data)
                .build();
    }
    /**
     * This methode is used to return list of 3 last projects
     * @return ProjectFundingDTO
     */
    @Override
    public List<ProjectFundingDTO> getAverageFundingByProject() {
        var projects = new ArrayList<ProjectFundingDTO>();

//        var listProjects = fundingRepository.averageFundingByProject();
//        for(int i=0; i<listProjects.size(); i++) {
//            if(i<3) projects.add(listProjects.get(i));
//            else break;
//        }
        return projects;
    }
}
