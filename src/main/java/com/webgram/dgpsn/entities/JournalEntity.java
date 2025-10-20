package com.webgram.dgpsn.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(name = "journal")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class JournalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "jnl_id")
    private Long id;

    @Column(name = "jnl_typeAction")
    private String actionType;

    @Column(name = "jnle_device_type")
    private String deviceType;

    @Column(name = "jnle_adresse_ip")
    private String ipAddress;

    @Column(name = "jnle_navigateur")
    private String navigateur;

    @Column(name = "jnle_user_agent")
    private String userAgent;

    @Column(name = "jnle_user")
    private String user;

    @Column(name = "jnle_lgin")
    private String login;

    @Column(name = "method_http")
    private String method;

    @Column(name = "url")
    String url;

    @Column(name = "page")
    private String page;

    @Column(name = "query_string")
    private String queryString;

    @Column(name = "referer_page")
    private String refererPage;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime creationDate;

    @Column(name = "data")
    private String data;

    @Column(name = "path")
    private String path;



}
