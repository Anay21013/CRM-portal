package com.erp.core.base;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
public abstract class BaseEntity extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="end_date")
	protected LocalDate endDate;

	@Value("false")
	protected Boolean deleted;

	@PreUpdate
	@PrePersist
	private void preInsert() {
		if(this.deleted == null) {
			this.deleted = false;
		}
	}
	
}
