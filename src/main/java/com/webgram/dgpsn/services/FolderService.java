package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.FolderDto;

import java.util.List;

public interface FolderService {
    FolderDto create(FolderDto folderDTO);
    FolderDto createSubFolder(Long parentId, FolderDto subFolder);
    FolderDto update(Long id, FolderDto folderDTO);
    FolderDto read(Long folderId);
    void delete(Long folderId);
    List<FolderDto> readParentsOnly();
    List<FolderDto> readAll();
}
