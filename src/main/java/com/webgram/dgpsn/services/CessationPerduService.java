package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.CessationPerduDTO;
import org.springframework.data.domain.Page;


import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CessationPerduService {
//   CessationFonctionDTO create(CessationFonctionDTO cessationFonctionDTO);
//   CessationFonctionDTO update(Long cessationId,CessationFonctionDTO cessationFonctionDTO);
   CessationPerduDTO readPerdu(Long congeId);
   List<CessationPerduDTO> readAll();
   Page<CessationPerduDTO> readPage(Map<String, String> searchParams, int page, int size) throws ParseException;
   void delete(Long cessationPerduId);


}
