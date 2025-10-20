package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.NoteFileEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.NoteFileMapper;
import com.webgram.dgpsn.models.NoteFileDTO;
import com.webgram.dgpsn.repositories.NoteFileRepository;
import com.webgram.dgpsn.services.NoteFileService;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoteFileServiceImpl implements NoteFileService {

    private static final String STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE = "id n'existe pas";
    private final NoteFileRepository noteFileRepository;
    private  final NoteFileMapper noteFileMapper;


    @Override
    public List<NoteFileDTO> createNoteFile(List<NoteFileDTO> noteFileDTO) {
//        var savedNoteFile = noteFileRepository.save(noteFileMapper.asEntity(noteFileDTO));
        //var savedNoteFile = noteFileRepository.saveAll(noteFileMapper.asEntity(noteFileDTO));

        var maper=noteFileMapper.parseToEntity(noteFileDTO);
        noteFileRepository.saveAll(maper);
        System.out.println("montre l'entite"+maper.iterator());
        Iterator<NoteFileEntity> map=maper.iterator();
//        while (map.hasNext()){
//            System.out.println("Bouna pene"+map.next());
//            noteFileRepository.saveAllAndFlush(maper);
//        }
        System.out.println("fariService"+noteFileDTO);
       return  null;

//        log.info("Structure successfully added {}", savedNoteFile);
//
//        return noteFileMapper.asDto(savedNoteFile);
    }

    @Override
    public List<NoteFileDTO> readNoteFileperdossier(Long id) {
//        if(!noteFileRepository.existsById(id)){
//            throw new ResourceNotFoundException(MessageFormat.format("BounaPena",+ id));
//        }
        var search=noteFileRepository.findByMarketFileEntityId(id);
        var test=search.iterator();

        while (test.hasNext()){
            System.out.println("montremoi"+ test.next());

        }

        return noteFileMapper.parse(search);
    }

    @Override
    public List<NoteFileDTO> updateNoteFile(List<NoteFileDTO> noteFileDTO,List<Long>noteId) {
        var note=noteFileDTO.iterator();
        List<NoteFileDTO> noteWithId=new ArrayList<>();
        var listIdNote= noteId.iterator();
        while (note.hasNext() && listIdNote.hasNext() ){
           var not= note.next();
           var id=listIdNote.next();

           var notes=not.setId(id);

            noteWithId.add(notes);

            if(!noteFileRepository.existsById(notes.getId())){
                throw new ResourceNotFoundException(MessageFormat.format(STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE,+ notes.getId()));
            }
        }

        var maper=noteFileMapper.parseToEntity(noteWithId);
        var noteFileMaper = noteFileRepository.saveAll(maper);

        var ntFile = noteFileMapper.parse(noteFileMaper);

        return ntFile;


    }

    @Override
    public NoteFileDTO readNoteFile(Long id) {
        var MarketFile = noteFileRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("le dossier n'existe", id));

        log.info("reading Passation de Plan id {}", id);
        System.out.println("Bouna pene"+noteFileMapper.asDto(MarketFile));
        return noteFileMapper.asDto(MarketFile);
    }

    @Override
    public void deleteNoteFile(Long id) {
        try {
            noteFileRepository.deleteById(id);
            log.info("The market file id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<NoteFileDTO> readAllNoteFile(Pageable pageable, Double note, String sortBy, Boolean ascending) {
        return noteFileRepository.readAllByFiltering(pageable,note,sortBy,ascending)
                .map(noteFileMapper::asDto);
    }
}
