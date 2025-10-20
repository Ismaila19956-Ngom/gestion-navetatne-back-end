package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.FolderMapper;
import com.webgram.dgpsn.models.FolderDto;
import com.webgram.dgpsn.repositories.DocumentPublicRepository;
import com.webgram.dgpsn.repositories.FolderRepository;
import com.webgram.dgpsn.services.FolderService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;
    private final DocumentPublicRepository documentPublicRepository;

    @Override
    public FolderDto create(FolderDto folderDTO) {
        var folder = folderMapper.asEntity(folderDTO);
        folder.setParentId(null);
        folder = folderRepository.save(folder);
        return folderMapper.asDto(folder);
    }

    @Override
    public FolderDto createSubFolder(Long parentId, FolderDto subFolder) {
        var parent = folderRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Impossible de trouver le repertoire parent id %d", parentId)));

        var child = folderMapper.asEntity(subFolder);
        child.setParentId(parentId);
        child = folderRepository.save(child);

        parent.getSubFolders().add(child);

        return folderMapper.asDto(parent);
    }

    @Override
    public FolderDto update(Long id, FolderDto folderDTO) {
        var parentId = folderRepository.findById(id).orElseThrow().getParentId();
        var folder = folderMapper.asEntity(folderDTO);
        folder.setId(id);
        folder.setParentId(parentId);
        return folderMapper.asDto(folderRepository.save(folder));
    }

    @Override
    public FolderDto read(Long folderId) {
        return folderMapper.asDto(
                folderRepository.findById(folderId)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Impossible de trouver le repertoire avec id %d", folderId))));
    }

    @Override
    public void delete(Long folderId) {
      try {
          if(!folderRepository.existsById(folderId)) {
              throw new RuntimeException(String.format("Impossible de trouver le repertoire avec id %d", folderId));
          }

          var folder = folderRepository.findById(folderId).orElseThrow();

          // if folder is a child then it has a parent
          if(Objects.nonNull(folder.getParentId())) {
              var parent = folderRepository.findById(folder.getParentId()).orElseThrow();

              parent.getSubFolders().remove(folder);
          }

          documentPublicRepository.deleteAllByFolderId(folderId);

          folderRepository.deleteById(folderId);

      } catch (IllegalArgumentException ex) {
          throw new RuntimeException(String.format("Impossible de trouver le repertoire avec id %d", folderId));
      }
    }

    @Override
    public List<FolderDto> readParentsOnly() {
        var folders = folderRepository.findAll()
                .stream()
                .filter(folder -> Objects.isNull(folder.getParentId()))
                .collect(Collectors.toList());

        return folderMapper.parse(folders);
    }

    @Override
    public List<FolderDto> readAll() {
        var folders = folderRepository.findAll()
                .stream()
                .collect(Collectors.toList());

        return folderMapper.parse(folders);
    }
}
