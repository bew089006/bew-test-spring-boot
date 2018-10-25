package com.ascendcorp.example.validator;

import org.apache.log4j.Logger;

import com.ascendcorp.exam.model.InquiryDTO;

/**
 * Validates inquiry input
 * 
 * @author wrsp
 *
 */
public class InquiryValidationRule implements InquiryRule {
	
	final static Logger log = Logger.getLogger(InquiryValidationRule.class);
	
	@Override
	public void validate(InquiryDTO inquiryM) throws Exception {
		log.info("validate request parameters.");
        if(inquiryM.getTransactionId() == null) {
            log.info("Transaction id is required!");
            throw new NullPointerException("Transaction id is required!");
        }
        if(inquiryM.getTranDateTime() == null) {
            log.info("Transaction DateTime is required!");
            throw new NullPointerException("Transaction DateTime is required!");
        }
        if(inquiryM.getChannel() == null) {
            log.info("Channel is required!");
            throw new NullPointerException("Channel is required!");
        }
        if(inquiryM.getBankCode() == null || inquiryM.getBankCode().equalsIgnoreCase("")) {
            log.info("Bank Code is required!");
            throw new NullPointerException("Bank Code is required!");
        }
        if(inquiryM.getBankNumber() == null || inquiryM.getBankNumber().equalsIgnoreCase("")) {
            log.info("Bank Number is required!");
            throw new NullPointerException("Bank Number is required!");
        }
        if(inquiryM.getAmount() <= 0) {
            log.info("Amount must more than zero!");
            throw new NullPointerException("Amount must more than zero!");
        }
		
	}

}
