package com.ascendcorp.exam.status;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InquiryServiceStatusTest {
	
	@Test
	public void should_returnApproved_when_code200() {
		String reasonPhrase = InquiryServiceStatus.APPROVED.getReasonPhrase();
		String value = InquiryServiceStatus.APPROVED.toString();
		
		assertEquals("approved",reasonPhrase);
		assertEquals("200", value);
	}
	
	@Test
	public void should_returnGeneralInvalidData_when_code400() {
		String reasonPhrase = InquiryServiceStatus.GENERAL_INVALID_DATA_400.getReasonPhrase();
		String value = InquiryServiceStatus.GENERAL_INVALID_DATA_400.toString();
		
		assertEquals("General Invalid Data", reasonPhrase);
		assertEquals("400", value);
	}
	
	@Test
	public void shold_returnGeneralTransactionError_when_code500() {
		String reasonPhrase = InquiryServiceStatus.GENERAL_TRANSACTION_ERROR_500.getReasonPhrase();
		String value = InquiryServiceStatus.GENERAL_TRANSACTION_ERROR_500.toString();
		
		assertEquals("General Transaction Error", reasonPhrase);
		assertEquals("500", value);
	}
	
	@Test
	public void shold_returnGeneralInvalidData_when_code500() {
		String reasonPhrase = InquiryServiceStatus.GENERAL_INVALID_DATA_500.getReasonPhrase();
		String value = InquiryServiceStatus.GENERAL_INVALID_DATA_500.toString();
		
		assertEquals("General Invalid Data", reasonPhrase);
		assertEquals("500", value);
	}
	
	@Test
	public void shold_returnGeneralInvalidData_when_code501() {
		String reasonPhrase = InquiryServiceStatus.GENERAL_INVALID_DATA_501.getReasonPhrase();
		String value = InquiryServiceStatus.GENERAL_INVALID_DATA_501.toString();
		
		assertEquals("General Invalid Data", reasonPhrase);
		assertEquals("501", value);
	}
	
	@Test
	public void shold_returnGeneralInvalidData_when_code503() {
		String reasonPhrase = InquiryServiceStatus.ERROR_TIMEOUT.getReasonPhrase();
		String value = InquiryServiceStatus.ERROR_TIMEOUT.toString();
		
		assertEquals("Error timeout", reasonPhrase);
		assertEquals("503", value);
	}
	
	@Test
	public void shold_returnGeneralInvalidData_when_code504() {
		String reasonPhrase = InquiryServiceStatus.INTERNAL_APPLICATION_ERROR.getReasonPhrase();
		String value = InquiryServiceStatus.INTERNAL_APPLICATION_ERROR.toString();
		
		assertEquals("Internal Application Error", reasonPhrase);
		assertEquals("504", value);
	}

}
