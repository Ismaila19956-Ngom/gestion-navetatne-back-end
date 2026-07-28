package sn.naavetane.backend.services.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Slf4j
@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InputStreamUtils {

    public InputStream generateInputStream(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            log.error("File not found: {}", filePath, e);
            throw new RuntimeException("File not found: " + filePath, e);
        }
    }
}
