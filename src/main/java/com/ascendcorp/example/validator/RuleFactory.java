package com.ascendcorp.example.validator;

import java.util.ArrayList;
import java.util.List;

public class RuleFactory {
	
	public static List<InquiryRule> getInquiryRule() throws Exception {
		List<InquiryRule> inquiryRules = new ArrayList<InquiryRule>();
			inquiryRules.add(new InquiryValidationRule());
		return inquiryRules;
	}
	
}
