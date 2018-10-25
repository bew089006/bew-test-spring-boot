package com.ascendcorp.example.validator;

import com.ascendcorp.exam.model.InquiryDTO;

public interface InquiryRule {
	
	void validate(InquiryDTO inquiry) throws Exception;

}
