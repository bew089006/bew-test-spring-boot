package com.ascendcorp.example.validator;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ascendcorp.exam.model.InquiryDTO;

public class InquiryValidationRuleTest {

    @Rule
	public ExpectedException thrown = ExpectedException.none();
    
	@Test
	public void should_throwNullPointerException_when_transactionIdIsNull() throws Exception {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Transaction id is required!");
		InquiryDTO inquiryM = new InquiryDTO(null, new Date(),
                "Mobile", null,
                "BANK1", "4321000", 100d, "rrivsffv234c",
                "11223xfgt", null, null);
		
		new InquiryValidationRule().validate(inquiryM);
	}
	
	@Test
	public void should_throwNullPointerException_when_tranDateTimeIsNull() throws Exception {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Transaction DateTime is required!");
		InquiryDTO inquiryM = new InquiryDTO("123456", null,
                "Mobile", null,
                "BANK1", "4321000", 100d, "rrivsffv234c",
                "11223xfgt", null, null);
		
		new InquiryValidationRule()
		.validate(inquiryM);
	}
	
	@Test
	public void should_throwNullPointerException_when_channelIsNull() throws Exception {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Channel is required!");
		InquiryDTO inquiryM = new InquiryDTO("123456", new Date(),
                null, null,
                "BANK1", "4321000", 100d, "rrivsffv234c",
                "11223xfgt", null, null);
		
		new InquiryValidationRule()
		.validate(inquiryM);
	}
	
	@Test
	public void should_throwNullPointerException_when_bankCodeIsNull() throws Exception {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Bank Code is required!");
		InquiryDTO inquiryM = new InquiryDTO("123456", new Date(),
                "mobile", null,
                null, "4321000", 100d, "rrivsffv234c",
                "11223xfgt", null, null);
		
		new InquiryValidationRule()
		.validate(inquiryM);
	}

	@Test
	public void should_throwNullPointerException_when_bankNumbeIsNull() throws Exception {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Bank Number is required!");
		InquiryDTO inquiryM = new InquiryDTO("123456", new Date(),
                "mobile", null,
                "BANK1", null, 100d, "rrivsffv234c",
                "11223xfgt", null, null);
		
		new InquiryValidationRule()
		.validate(inquiryM);
	}
	
	@Test
	public void should_throwNullPointerException_when_amountLessThan1() throws Exception {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Amount must more than zero!");
		InquiryDTO inquiryM = new InquiryDTO("123456", new Date(),
                "mobile", null,
                "BANK1", "4321000", 0d, "rrivsffv234c",
                "11223xfgt", null, null);
		
		new InquiryValidationRule()
		.validate(inquiryM);
	}
	
}
