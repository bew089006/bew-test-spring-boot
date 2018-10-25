package com.ascendcorp.exam.status;

/**
 * Enumeration Inquiry service status code
 * 
 * @author wrsp
 *
 */
public enum InquiryServiceStatus {
	
	/**
	 * {@code 200 approved}.
	 */
	APPROVED(200, "approved"),
	
	/**
	 * {@code 400 General Invalid Data}.
	 */
	GENERAL_INVALID_DATA_400(400, "General Invalid Data"),
	
	/**
	 * {@code 500 General Transaction Error}.
	 */
	GENERAL_TRANSACTION_ERROR_500(500, "General Transaction Error"),
	
	/**
	 * {@code 500 General Invalid Data}.
	 */
	GENERAL_INVALID_DATA_500(500, "General Invalid Data"),
	
	/**
	 * {@code 501 General Invalid Data}.
	 */
	GENERAL_INVALID_DATA_501(501, "General Invalid Data"),
	
	/**
	 * {@code 503 Error timeout}.
	 */
	ERROR_TIMEOUT(503, "Error timeout"),
	
	/**
	 * {@code 504 Internal Application Error}.
	 */
	INTERNAL_APPLICATION_ERROR(504, "Internal Application Error");
	
	private final int value;
	
	private final String reasonPhrase;
	
	InquiryServiceStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}
	
//	/**
//	 * Return the integer value of this status code.
//	 */
//	public int value() {
//		return this.value;
//	}
	
	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}
	
	/**
	 * Return a string  of this status code.
	 */
	@Override
	public String toString() {
		return Integer.toString(this.value);
	}
	
//	/**
//	 * Return the enum constant of this type with the specified numeric value.
//	 * @param statusCode the numeric value of the enum to be returned
//	 * @return the enum constant with the specified numeric value
//	 * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
//	 */
//	public static InquiryServiceStatus valueOf(int statusCode) {
//		for (InquiryServiceStatus status : values()) {
//			if(status.value == statusCode) {
//				return status;
//			}
//		}
//		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
//	}

}
