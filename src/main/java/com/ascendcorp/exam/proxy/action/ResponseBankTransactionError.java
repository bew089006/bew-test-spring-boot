package com.ascendcorp.exam.proxy.action;

import org.apache.log4j.Logger;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import com.ascendcorp.exam.service.InquiryService;
import com.ascendcorp.exam.status.InquiryServiceStatus;

public class ResponseBankTransactionError implements ResponseBankAction {
	
	final static Logger log = Logger.getLogger(InquiryService.class);

	@Override
	public void processManual(TransferResponse transferResponse,
			InquiryServiceResultDTO respDTO) {
		// bank response code = transaction_error
        String replyDesc = transferResponse.getDescription();
        if(replyDesc != null) {
            String respDesc[] = replyDesc.split(":");
            if(respDesc != null && respDesc.length >= 2) {
                log.info("Case Inquiry Error Code Format Now Will Get From [0] and [1] first");
                String subIdx1 = respDesc[0];
                String subIdx2 = respDesc[1];
                log.info("index[0] : "+subIdx1 + " index[1] is >> "+subIdx2);
                
                if("98".equalsIgnoreCase(subIdx1)) {
                    // bank code 98
                    respDTO.setReasonCode(subIdx1);
                    respDTO.setReasonDesc(subIdx2);
                } else {
                    log.info("case error is not 98 code");
                    if(respDesc.length >= 3) {
                        // bank description full format
                        String subIdx3 = respDesc[2];
                        log.info("index[0] : "+subIdx3);
                        respDTO.setReasonCode(subIdx2);
                        respDTO.setReasonDesc(subIdx3);
                    } else {
                        // bank description short format
                        respDTO.setReasonCode(subIdx1);
                        respDTO.setReasonDesc(subIdx2);
                    }
                }
            } else {
                // bank description incorrect format
                respDTO.setReasonCode(InquiryServiceStatus.GENERAL_TRANSACTION_ERROR_500.toString());
                respDTO.setReasonDesc(InquiryServiceStatus.GENERAL_TRANSACTION_ERROR_500.getReasonPhrase());
            }
        } else {
            // bank no description
        		respDTO.setReasonCode(InquiryServiceStatus.GENERAL_TRANSACTION_ERROR_500.toString());
        		respDTO.setReasonDesc(InquiryServiceStatus.GENERAL_TRANSACTION_ERROR_500.getReasonPhrase());
        }		
	}

}
