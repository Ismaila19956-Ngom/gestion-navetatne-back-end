package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.MarketFileEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.MarketFileMapper;
import com.webgram.dgpsn.models.MarketFileDTO;
import com.webgram.dgpsn.models.NoteFileDTO;
import com.webgram.dgpsn.repositories.MarketFileRepository;
import com.webgram.dgpsn.services.MarketFileService;

import java.text.MessageFormat;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MarketFileServiceImpl implements MarketFileService {

    private static final String STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE = "id n'existe pas";
    private  final MarketFileRepository marketFileRepository;
    private  final MarketFileMapper marketFileMapper;


    @Override
    public MarketFileDTO createMarketFile(MarketFileDTO marketFileDTO) {
        var savedMarketFile = marketFileRepository.save(marketFileMapper.asEntity(marketFileDTO));

        log.info("Structure successfully added {}", savedMarketFile);

        return marketFileMapper.asDto(savedMarketFile);
    }

    @Override
    public MarketFileDTO updateMarketFile(MarketFileDTO marketFileDTO) {
        if(!marketFileRepository.existsById(marketFileDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE, marketFileDTO.getId()));
        }

        var marketFile = marketFileMapper.asEntity(marketFileDTO);

        var updatedmarketFile = marketFileMapper.asDto(marketFileRepository.save(marketFile));

        log.info("Structure successfully updated {} ", updatedmarketFile.getId());

        return updatedmarketFile;
    }

    @Override
    public MarketFileDTO readMarketFile(Long id) {
        var MarketFile = marketFileRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("le dossier n'existe", id));

        log.info("reading Passation de Plan id {}", id);

        return marketFileMapper.asDto(MarketFile);
    }

    @Override
    public void deleteMarketFile(Long id) {
        try {
            marketFileRepository.deleteById(id);
            log.info("The market file id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public List<MarketFileDTO> readAllMarketWithNtes() {
        var ts=marketFileRepository.findAllWithNotes();

       var dto=marketFileMapper.parse(ts);



        // Parcourir la liste des MarketFileDTO
        for (MarketFileDTO marketFileDTO : dto) {
            // Récupérer la liste des NoteFileDTO pour chaque MarketFileDTO
            List<NoteFileDTO> noteFileDTOs = marketFileDTO.getNoteFileEntities();

            // Initialiser la somme des notes et des pondérations
            double totalNote = 0.0;
            double totalWeight = 0.0;

            // Parcourir la liste des NoteFileDTO pour calculer la note finale
            for (NoteFileDTO noteFileDTO : noteFileDTOs) {
                // Récupérer la note et la pondération du critère associé
                double note = noteFileDTO.getNote();
                double weight=noteFileDTO.getPassationMarketCritere().getPonderation();
                //double weight = noteFileDTO.getCriteria().getWeight(); // Suppose que vous avez une méthode pour récupérer la pondération du critère

                // Ajouter la note pondérée à la somme des notes
                totalNote += (note * weight);

                // Ajouter la pondération à la somme des pondérations
                totalWeight += weight;
            }

            // Calculer la note finale en divisant la somme des notes par la somme des pondérations
            double finalNote = (totalWeight != 0) ? (totalNote / totalWeight) : 0.0;

            System.out.println("note final"+finalNote);

            // Mettre à jour la note finale du MarketFileDTO
            marketFileDTO.setNoteFinal(finalNote);
        }

       return dto;
    }

    @Override
    public Page<MarketFileDTO> readAllMarketFile(Pageable pageable, String fileNumber, String email,Long passationMarketId, String sortBy, Boolean ascending) {
//        return marketFileRepository.readAllByFiltering(pageable,fileNumber,email,passationMarketId,sortBy,ascending)
//                .map(marketFileMapper::asDto);

        Page<MarketFileEntity> marketFilesPage = marketFileRepository.readAllByFiltering(pageable, fileNumber, email, passationMarketId, sortBy, ascending);

        Page<MarketFileDTO> marketFilesDTOPage = marketFilesPage.map(marketFileMapper::asDto);

        marketFilesDTOPage.forEach(marketFileDTO -> {
            List<NoteFileDTO> noteFileDTOs = marketFileDTO.getNoteFileEntities();

            double totalNote = 0.0;
            double totalWeight = 0.0;

            for (NoteFileDTO noteFileDTO : noteFileDTOs) {
                double note = noteFileDTO.getNote();
                double weight = noteFileDTO.getPassationMarketCritere().getPonderation();
                totalNote += (note * weight);
                totalWeight += weight;
            }

            double finalNote = (totalWeight != 0) ? (totalNote / totalWeight) : 0.0;

            marketFileDTO.setNoteFinal(finalNote);
        });

        return marketFilesDTOPage;
    }
}
