package com.webgram.dgpsn.models.requests;

import lombok.Data;
import com.webgram.dgpsn.entities.CadreLogiqueEntity;

import java.util.List;

@Data
public class geographicalLocationDTO {
    Long projetId;
    List<CadreLogiqueEntity> cadreLogiques;
}
