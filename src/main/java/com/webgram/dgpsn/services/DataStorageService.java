package com.webgram.dgpsn.services;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public interface DataStorageService {

    String storeFile(String type, String reference, String expention, InputStream inputStream);
    Resource loadFile(String filePath) throws IOException;
    String storeFileRelativePath(String type, String reference, String extension, InputStream inputStream);
    Resource loadFileRelativePath(String filePath) throws IOException;
}
