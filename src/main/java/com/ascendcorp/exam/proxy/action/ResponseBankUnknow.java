package com.ascendcorp.exam.proxy.action;

import org.apache.log4j.Logger;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import com.ascendcorp.exam.service.InquiryService;
import com.ascendcorp.exam.status.InquiryServiceStatus;

public class ResponseBankUnknow implements ResponseBankAction {
	
	final static Logger log = Logger.getLogger(InquiryService.class);

	@Override
	public void processManual(TransferResponse transferResponse,
			InquiryServiceResultDTO respDTO) {
		String replyDesc = transferResponse.getDescription();
        
        if(replyDesc != null) {
            String respDesc[] = replyDesc.split(":");
            if(respDesc != null && respDesc.length >= 2) {
                // bank description full format
                respDTO.setReasonCode(respDesc[0]);
                respDTO.setReasonDesc(respDesc[1]);
                if(respDTO.getReasonDesc() == null || respDTO.getReasonDesc().trim().length() == 0) {
                    respDTO.setReasonDesc("General Invalid Data");
                }
            } else {
                // bank description short format
                respDTO.setReasonCode(InquiryServiceStatus.GENERAL_INVALID_DATA_501.toString());
                respDTO.setReasonDesc(InquiryServiceStatus.GENERAL_INVALID_DATA_501.getReasonPhrase());
            }
        } else {
            // bank no description
            respDTO.setReasonCode(InquiryServiceStatus.GENERAL_INVALID_DATA_501.toString());
            respDTO.setReasonDesc(InquiryServiceStatus.GENERAL_INVALID_DATA_501.getReasonPhrase());
        }	
	}

}
