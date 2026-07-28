package sn.naavetane.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import sn.naavetane.backend.entities.enums.TypeStructure;
import sn.naavetane.backend.models.StructureDTO;

import java.io.PrintWriter;
import java.util.List;

public interface StructureService {
    StructureDTO createStructure(StructureDTO structureDTO);
    StructureDTO updateStructure(StructureDTO structureDTO);
    StructureDTO readStructure(Long id);
    void deleteStructure(Long id);
    Page<StructureDTO> readAllStructure(
            Pageable pageable,
            String code,
            String nom,
            TypeStructure typeStructure,
            List<Long> idsToIgnore,
            List<TypeStructure> structureTypeList,
            String sortBy,
            Boolean ascending
    );

    StructureDTO readStructure(String code);

    

    // export removed
}


