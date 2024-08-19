package com.erp.core.base;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import com.erp.core.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = Constants.DATE_FORMAT_JSON)
	private LocalDate endDate;

	@Value("false")
	private Boolean deleted;

	protected String createdBy;

	@JsonFormat(pattern = Constants.DATETIME_FORMAT_JSON)
	protected LocalDateTime createdDatetime;

	protected String lastModifiedBy;

	@JsonFormat(pattern = Constants.DATETIME_FORMAT_JSON)
	protected LocalDateTime lastModifiedDatetime;

	protected void populateBaseDTO(BaseEntity baseEntity) {
		this.createdBy = baseEntity.getCreatedBy();
		this.createdDatetime = baseEntity.getCreatedDatetime();
		this.lastModifiedBy = baseEntity.getLastModifiedBy();
		this.lastModifiedDatetime = baseEntity.getLastModifiedDatetime();
		this.endDate = baseEntity.getEndDate();
		this.deleted = baseEntity.getDeleted();
		if (this.deleted == null) {
			this.deleted = Boolean.FALSE;
		}
	}

	public void populateBaseEntity(BaseEntity baseEntity) {
		baseEntity.setCreatedBy(getCreatedBy());
		baseEntity.setCreatedDatetime(getCreatedDatetime());
		baseEntity.setLastModifiedBy(getLastModifiedBy());
		baseEntity.setLastModifiedDatetime(getLastModifiedDatetime());
		baseEntity.setEndDate(getEndDate());
		baseEntity.setDeleted(getDeleted());
		if (getDeleted() == null) {
			baseEntity.setDeleted(Boolean.FALSE);
		}
	}
}
