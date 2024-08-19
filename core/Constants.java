package com.erp.core;

public class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class");
	}

	public static final String DATE_FORMAT_JSON = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT_JSON = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "dd-MMM-yyyy";
	
	public static final String FTE_ROLE = "FTE";
	
	public static final String BATCH_STATUS_SUCCESS = "Success";
	public static final String BATCH_STATUS_FAILED = "Failed";
	
	public static final String ACCOUNTING_EMAIL = "accounting@ensylon.com";
	public static final String MANOJ_EMAIL = "manoj.aggarwal@ensylon.com";
	
//	public static final int EXPIRATION = 60 * 24;
//	
//	public static final String TOKEN_INVALID = "invalidToken";
//    public static final String TOKEN_EXPIRED = "expired";
//    public static final String TOKEN_VALID = "valid";
    
//    public static final String ACC_VERIFICATION = "Account Verification";
//    public static final String FORGOT_PWD = "Forgot Password";
}
