package com.erp.crm.master.rate;

public enum ERoleRateFrequency {
	PER_HOUR(1), PER_DAY(2), PER_MONTH(3), PER_QTR(4), PER_YEAR(5);

	private final int roleRateFrequency;

	ERoleRateFrequency(int roleRateFrequency) {
		this.roleRateFrequency = roleRateFrequency;
	}

	public int getValue() {
		return roleRateFrequency;
	}

	public static String getRoleRateFrequencyName(int roleRateFrequency) {

		ERoleRateFrequency[] eroleRateFrequency = ERoleRateFrequency.values();
		for (ERoleRateFrequency el : eroleRateFrequency) {
			if (el.ordinal() == (roleRateFrequency - 1)) {
				return el.name();
			}
		}
		return "";
	}
}
