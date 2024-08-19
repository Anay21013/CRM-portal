package com.erp.core.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditable<U> implements Serializable {
	private static final long serialVersionUID = 1L;

	@CreatedBy
	@Column(name = "created_by")
	protected U createdBy;

	@CreatedDate
	@Column(name = "created_datetime")
	protected LocalDateTime createdDatetime;

	@LastModifiedBy
	@Column(name = "last_modified_by")
	protected U lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_datetime")
	protected LocalDateTime lastModifiedDatetime;

}
