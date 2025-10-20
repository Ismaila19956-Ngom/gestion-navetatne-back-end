package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoteFileRule {
    static final String NOTE_FILE_PREFIX = "/notefile";

    static final String NOTE_FILE_ID = "/{noteId}";


    static final String FOLDER_STEP="/folder";

    static final String FOLDER_FILE_ID="/{folderId}";

    static  final  String MARKET_ID="/{marketId}";






    @Bean
    public SecurityRule createNoteFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(NOTE_FILE_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_NOTE_FILE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readNoteId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NOTE_FILE_PREFIX +NOTE_FILE_ID )
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_NOTE_FILE)
//                .hasPermission(SecurityPermissions.READ_NOTE_FILE)
//                .hasPermission(SecurityPermissions. EDIT_NOTE_FILE)
//                .hasPermission(SecurityPermissions.DELETE_NOTE_FILE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule readFolderId() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NOTE_FILE_PREFIX +FOLDER_STEP+FOLDER_FILE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_NOTE_FILE)
//                .hasPermission(SecurityPermissions.READ_NOTE_FILE)
//                .hasPermission(SecurityPermissions. EDIT_NOTE_FILE)
//                .hasPermission(SecurityPermissions.DELETE_NOTE_FILE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readNoteFiles() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(NOTE_FILE_PREFIX)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.ADD_NOTE_FILE)
//                .hasPermission(SecurityPermissions.READ_NOTE_FILE)
//                .hasPermission(SecurityPermissions. EDIT_NOTE_FILE)
//                .hasPermission(SecurityPermissions.DELETE_NOTE_FILE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateNoteFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(NOTE_FILE_PREFIX+ NOTE_FILE_ID +MARKET_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.EDIT_NOTE_FILE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule deleteNoteFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(NOTE_FILE_PREFIX+ NOTE_FILE_ID)
                .build()
                .condition()
//                .hasPermission(SecurityPermissions.DELETE_NOTE_FILE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

}
