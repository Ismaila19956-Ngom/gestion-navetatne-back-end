package sn.naavetane.backend.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.naavetane.backend.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentPublicRule {
    static final String DOCUMENT_API_PREFIX = "/documents-publics";
    static final String DOCUMENT_ID = "/{documentId}";
    static final String PUBLISH_UNPUBLISH = "/publishUnpublish";
    static final String DOWNLOAD = "/_download";
    static final String PUBLISHED_DOCUMENTS = "/publishedDocuments";

    @Bean
    public SecurityRule createDocumentPublic() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DOCUMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readDocumentPublic() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DOCUMENT_API_PREFIX + DOCUMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_DOCUMENT)
                .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                .hasPermission(SecurityPermissions.DELETE_DOCUMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule downloadDocumentPublicFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DOCUMENT_API_PREFIX + DOCUMENT_ID + DOWNLOAD)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readAllDocumentPublic() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .authenticated(false)
                .apiPattern(DOCUMENT_API_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_DOCUMENT)
                .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                .hasPermission(SecurityPermissions.DELETE_DOCUMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readPublishedDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .authenticated(false)
                .apiPattern(DOCUMENT_API_PREFIX + PUBLISHED_DOCUMENTS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_DOCUMENT)
                .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                .hasPermission(SecurityPermissions.DELETE_DOCUMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule updateDocumentPublic() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DOCUMENT_API_PREFIX + DOCUMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    
    @Bean
    public SecurityRule deleteDocumentPublic() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DOCUMENT_API_PREFIX + DOCUMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.DELETE_DOCUMENT)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule publishOrUnpublishDocumentPublic() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DOCUMENT_API_PREFIX + PUBLISH_UNPUBLISH + DOCUMENT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
}
