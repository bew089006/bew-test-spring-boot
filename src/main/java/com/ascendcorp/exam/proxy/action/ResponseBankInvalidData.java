package com.ascendcorp.exam.proxy.action;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import com.ascendcorp.exam.status.InquiryServiceStatus;

public class ResponseBankInvalidData implements ResponseBankAction {

	@Override
	public void processManual(TransferResponse transferResponse,
			InquiryServiceResultDTO respDTO) {
		// bank response code = invalid_data
        String replyDesc = transferResponse.getDescription();
        if(replyDesc != null) {
            String respDesc[] = replyDesc.split(":");
            if(respDesc != null && respDesc.length >= 3) {
                // bank description full format
	            	respDTO.setReasonCode(respDesc[1]);
	            	respDTO.setReasonDesc(respDesc[2]);
            } else {
                // bank description short format
	            	respDTO.setReasonCode(InquiryServiceStatus.GENERAL_INVALID_DATA_400.toString());
	            	respDTO.setReasonDesc(InquiryServiceStatus.GENERAL_INVALID_DATA_400.getReasonPhrase());
            }
        } else {
            // bank no description
	        	respDTO.setReasonCode(InquiryServiceStatus.GENERAL_INVALID_DATA_400.toString());
	        	respDTO.setReasonDesc(InquiryServiceStatus.GENERAL_INVALID_DATA_400.getReasonPhrase());
        }		
	}

}
