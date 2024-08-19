package com.erp.crm.master;

public enum EPaymentTerms {
	SEVEN_DAYS(1), FIFTEEN_DAYS(2), THIRTY_DAYS(3), FORTYFIVE_DAYS(4), SIXTY_DAYS(5), NINTY_DAYS(6);

	private final int paymentTerms;

	EPaymentTerms(int paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public int getValue() {
		return paymentTerms;
	}

	public static String getPartyTypeName(int paymentTerms) {

		EPaymentTerms[] epaymentTerms = EPaymentTerms.values();
		for (EPaymentTerms el : epaymentTerms) {
			if (el.ordinal() == (paymentTerms - 1)) {
				return el.name();
			}
		}
		return "";
	}
}
