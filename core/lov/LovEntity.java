package com.erp.core.lov;

import java.io.Serializable;

import com.erp.core.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lov")
@NamedQuery(name = "LovEntity.findAll", query = "SELECT l FROM LovEntity l")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class LovEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LovPK id;
	
	@NotNull
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "short_text")
	@NotNull
	private String shortText;

	private String description;
	
	@Column(name = "ui_enabled")
	private Boolean uiEnabled;

}
