package com.erp.core.lov;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The primary key class for the lov database table.
 * 
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LovPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(insertable = false, updatable = false)
	private String type;

	private Integer code;

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LovPK)) {
			return false;
		}
		LovPK castOther = (LovPK) other;
		return this.type.equals(castOther.type) && this.code.equals(castOther.code);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.type.hashCode();
		hash = hash * prime + this.code.hashCode();

		return hash;
	}
}