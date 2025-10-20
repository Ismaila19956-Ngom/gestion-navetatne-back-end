package com.webgram.dgpsn.entities.audits;

import com.querydsl.core.annotations.QueryExclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@QueryExclude
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected T createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    protected LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "modified_by")
    protected T lastModifiedBy;

    @LastModifiedDate
    @Column(name = "modified_date")
    protected LocalDateTime lastModifiedDate;
}