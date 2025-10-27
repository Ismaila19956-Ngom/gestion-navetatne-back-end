package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.services.DataStorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataStorageServiceImpl implements DataStorageService {

    final DocumentProperties documentProperties;

    @Override
    public String storeFile(String type, String reference, String extension, InputStream inputStream) {

        /* Computing absoluteFilePath */
        final var absoluteFilePath = Paths.get(documentProperties.getFileStorageRootPath(), type,
                reference + FilenameUtils.EXTENSION_SEPARATOR + extension);

        /* Creating parent if not exist */
        absoluteFilePath.getParent().toFile().mkdirs();

        try {
        /* Storing file */
            Files.copy(inputStream, absoluteFilePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            log.error("An occurred during file storage: {} {}", absoluteFilePath, e);
        }

        return absoluteFilePath.toString();
    }

    public Resource loadFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("File not found or not readable: " + filePath);
        }
    }

    @Override
    public String storeFileRelativePath(String type, String reference, String extension, InputStream inputStream) {
        /* Computing absoluteFilePath */
        final var absoluteFilePath = Paths.get(documentProperties.getFileStorageRootPath(), type,
                reference + FilenameUtils.EXTENSION_SEPARATOR + extension);

        /* Creating parent if not exist */
        absoluteFilePath.getParent().toFile().mkdirs();

        try {
            /* Storing file */
            Files.copy(inputStream, absoluteFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("An error occurred during file storage: {} {}", absoluteFilePath, e);
            throw new RuntimeException("Failed to store file: " + absoluteFilePath, e);
        }

        /* Return relative path */
        return Paths.get(type, reference + FilenameUtils.EXTENSION_SEPARATOR + extension).toString().replace("\\", "/");
    }

    @Override
    public Resource loadFileRelativePath(String filePath) throws IOException {
        /* Reconstruct absolute path */
        Path path = Paths.get(documentProperties.getFileStorageRootPath(), filePath);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("File not found or not readable: " + filePath);
        }
    }
}
