package com.erp.core.lov;

import java.io.Serializable;

import com.erp.core.base.BaseDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Lov extends BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String type;
	private Integer code;
	private Long orgId;
	private String shortText;
	private String description;
	private Boolean uiEnabled;

	public Lov(LovEntity lovEntity) {

		this.type = lovEntity.getId().getType();
		this.code = lovEntity.getId().getCode();
		this.orgId = lovEntity.getOrgId();
		this.shortText = lovEntity.getShortText();
		this.description = lovEntity.getDescription();
		this.uiEnabled = lovEntity.getUiEnabled();
		super.populateBaseDTO(lovEntity);
	}

	public LovEntity populateLovEntity() {
		LovEntity lovEntity = new LovEntity();
		super.populateBaseEntity(lovEntity);

		lovEntity.setId(new LovPK(getType(), getCode()));
		lovEntity.setOrgId(getOrgId());
		lovEntity.setShortText(getShortText());
		lovEntity.setDescription(getDescription());
		lovEntity.setUiEnabled(getUiEnabled());
		return lovEntity;
	}

}
