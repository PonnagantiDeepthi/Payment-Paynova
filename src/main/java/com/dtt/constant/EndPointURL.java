package com.dtt.constant;

public final class EndPointURL {
	
	private EndPointURL() {} // prevent instantiation
	
	public static final String MAKE_PAYMENT = "/initiate-payment";

	public static final String GET_PAYMENT_HISTORY = "/user/{userId}";
	//public static final String DELETE_PAYMENT_HISTORY = "/user/{userId}";
	public static final String SEARCH_PAYMENT_HISTORY_FILTER="/api/payment-history/search";
	
	public static final String GET_PAYMENT_RECORD_BY_REFERENCE_TRANSACTION_ID = "/api/get/payment-record/by-paymentReference/{paymentReference}";
	public static final String GET_PAYMENT_RECORD_BY_TRANSACTION_ID= "/api/get/payment-record/by-transactionId/{transactionId}";
	
	
	//Pay Nova 
	public static final String PAY_NOVA_HEALTH_CHECK = "/api/Health/status";
}
