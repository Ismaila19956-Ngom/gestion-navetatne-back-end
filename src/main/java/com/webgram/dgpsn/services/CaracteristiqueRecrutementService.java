package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.CaracteristiqueRecrutementDTO;
import com.webgram.dgpsn.models.CaracteristiqueRecrutementDTO;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CaracteristiqueRecrutementService {
   CaracteristiqueRecrutementDTO create(CaracteristiqueRecrutementDTO caracteristiqueRecrutementDTO);

   CaracteristiqueRecrutementDTO update(CaracteristiqueRecrutementDTO caracteristiqueRecrutementDTO);

   CaracteristiqueRecrutementDTO read(Long caracteristiqueRecrutementId);
   List<CaracteristiqueRecrutementDTO> readAll();
   Page<CaracteristiqueRecrutementDTO> readPageCcaracteristiqueRecrutement(Map<String, String> searchParams, int page, int size) throws ParseException;
   void delete(Long caracteristiqueRecrutementId);



//   String traitementSoldeRestant();

}
