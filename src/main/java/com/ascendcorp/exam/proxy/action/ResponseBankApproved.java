package com.ascendcorp.exam.proxy.action;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import com.ascendcorp.exam.status.InquiryServiceStatus;

public class ResponseBankApproved implements ResponseBankAction {

	@Override
	public void processManual(TransferResponse transferResponse,
			InquiryServiceResultDTO respDTO) {
		// bank response code = approved
		respDTO.setReasonCode(InquiryServiceStatus.APPROVED.toString());
		respDTO.setReasonDesc(transferResponse.getDescription());
		respDTO.setAccountName(transferResponse.getDescription());		
	}

}
