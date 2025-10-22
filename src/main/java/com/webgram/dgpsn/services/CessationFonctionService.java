package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import com.webgram.dgpsn.models.CessationFonctionDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CessationFonctionService {
   CessationFonctionDTO create(CessationFonctionDTO cessationFonctionDTO);

   CessationFonctionDTO update(Long cessationId,CessationFonctionDTO cessationFonctionDTO);

   CessationFonctionDTO read(Long congeId);
   List<CessationFonctionDTO> readAll();
   Page<CessationFonctionDTO> readPage(Map<String, String> searchParams, int page, int size) throws ParseException;
   void delete(Long cessationId);

   Integer getLatestSoldeAnnuelByCongeIdAndMatricule(Long congeId);

   Integer getLatestDureeSoldeByCongeId(Long congeId);

//   String traitementSoldeRestant();
String traitementSoldeRestant(int anneeSelectionnee);

}
