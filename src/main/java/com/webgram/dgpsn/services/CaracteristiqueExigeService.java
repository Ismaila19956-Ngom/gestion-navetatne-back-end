package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.CaracteristiqueExigeDTO;
import com.webgram.dgpsn.models.CaracteristiqueExigeDTO;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CaracteristiqueExigeService {
   CaracteristiqueExigeDTO create(CaracteristiqueExigeDTO caracteristiqueExigeDTO);

   CaracteristiqueExigeDTO update(CaracteristiqueExigeDTO caracteristiqueExigeDTO);

   CaracteristiqueExigeDTO read(Long caracteristiqueExigeId);
   List<CaracteristiqueExigeDTO> readAll();
   Page<CaracteristiqueExigeDTO> readPageCaracteristiqueExige(Map<String, String> searchParams, int page, int size) throws ParseException;
   void delete(Long caracteristiqueExigeId);

}
