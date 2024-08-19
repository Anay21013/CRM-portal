package com.erp.crm.master.customer.contract;

public enum EContractType {
	FIXED_PRICE(1), EFFORT_BASED(2);

	private final int contractType;

	EContractType(int contractType) {
		this.contractType = contractType;
	}

	public int getValue() {
		return contractType;
	}

	public static String getContractTypeName(int contractType) {

		EContractType[] econtractType = EContractType.values();
		for (EContractType el : econtractType) {
			if (el.ordinal() == (contractType - 1)) {
				return el.name();
			}
		}
		return "";
	}
}
