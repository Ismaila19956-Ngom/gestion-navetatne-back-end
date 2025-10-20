package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.NoteFileDTO;

import java.util.List;

public interface NoteFileService {
    List<NoteFileDTO> createNoteFile(List<NoteFileDTO> noteFileDTO);

    List<NoteFileDTO> readNoteFileperdossier(Long id);
   List <NoteFileDTO> updateNoteFile(List<NoteFileDTO> marketFileDTO,List<Long>noteId);
    NoteFileDTO readNoteFile(Long id);
    void deleteNoteFile(Long id);

    Page<NoteFileDTO> readAllNoteFile(
            Pageable pageable,
            Double note,
            String sortBy,
            Boolean ascending
    );


}
