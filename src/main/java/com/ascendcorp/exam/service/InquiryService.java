package com.ascendcorp.exam.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.WebServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ascendcorp.exam.model.InquiryDTO;
import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import com.ascendcorp.exam.proxy.BankProxyGateway;
import com.ascendcorp.exam.proxy.action.ResponseBankAction;
import com.ascendcorp.exam.proxy.action.ResponseBankApproved;
import com.ascendcorp.exam.proxy.action.ResponseBankInvalidData;
import com.ascendcorp.exam.proxy.action.ResponseBankTransactionError;
import com.ascendcorp.exam.proxy.action.ResponseBankUnknow;
import com.ascendcorp.exam.status.InquiryServiceStatus;
import com.ascendcorp.example.validator.InquiryRule;
import com.ascendcorp.example.validator.RuleFactory;

public class InquiryService {

    @Autowired
    private BankProxyGateway bankProxyGateway;

    final static Logger log = Logger.getLogger(InquiryService.class);
    
    /**
     * Rule from RuleFactory
     *  - Validates inquiry input
     * 
     * @param inquiryM
     * @throws Exception 
     */
    private void validate(InquiryDTO inquiryM) throws Exception {
    		for(InquiryRule inquiryRule : RuleFactory.getInquiryRule()) {
    			inquiryRule.validate(inquiryM);
    		}
	}

    public InquiryServiceResultDTO inquiry(String transactionId,
                                           Date tranDateTime,
                                           String channel,
                                           String locationCode,
                                           String bankCode,
                                           String bankNumber,
                                           double amount,
                                           String reference1,
                                           String reference2,
                                           String firstName,
                                           String lastName)
    {
        InquiryServiceResultDTO respDTO = null;
        try {
        		InquiryDTO inquiryM = new InquiryDTO(transactionId, tranDateTime, channel, locationCode, bankCode, bankNumber, amount, reference1, reference2, firstName, lastName);
        		// Validates inquiry input before call bank web service
        		this.validate(inquiryM);
        		
    			log.info("call bank web service");
            TransferResponse response = bankProxyGateway.requestTransfer(transactionId, tranDateTime, channel,
                    bankCode, bankNumber, amount, reference1, reference2);

            log.info("check bank response code");
            if(response != null) /* New */ {
                log.debug("found response code");
                respDTO = new InquiryServiceResultDTO();
	                respDTO.setRef_no1(response.getReferenceCode1());
	                respDTO.setRef_no2(response.getReferenceCode2());
	                respDTO.setAmount(response.getBalance());
	                respDTO.setTranID(response.getBankTransactionID());
                
                String responseCodeCondition = !StringUtils.isEmpty(response.getResponseCode()) ? response.getResponseCode().toLowerCase() : response.getResponseCode();
                
                Map<String, ResponseBankAction> conditionMap = new HashMap<String, ResponseBankAction>();
	                conditionMap.put("approved", new ResponseBankApproved());
	                conditionMap.put("invalid_data", new ResponseBankInvalidData());
	                conditionMap.put("transaction_error", new ResponseBankTransactionError());
	                conditionMap.put("unknown", new ResponseBankUnknow());
                
                if(conditionMap.containsKey(responseCodeCondition)) {
                		conditionMap.get(responseCodeCondition).processManual(response, respDTO);
                } else {
                		// bank code not support
                		throw new Exception("Unsupport Error Reason Code");
                }
                
            } else {
                // no resport from bank
                throw new Exception("Unable to inquiry from service.");
            }
        } catch(NullPointerException ne) {
        		respDTO = this.inquiryNullPointerExeption(respDTO);
        } catch(WebServiceException r) {
        		respDTO = this.inquiryWebServiceException(respDTO, r);
        } catch(Exception e) {
            log.error("inquiry exception", e);
            respDTO = this.inquiryException(respDTO);
        }
        
        return respDTO;
    }

	private InquiryServiceResultDTO inquiryException(InquiryServiceResultDTO respDTO) {
		if(respDTO == null || (respDTO != null && respDTO.getReasonCode() == null)) {
		    respDTO = new InquiryServiceResultDTO(
		    		InquiryServiceStatus.INTERNAL_APPLICATION_ERROR.toString(),
		    		InquiryServiceStatus.INTERNAL_APPLICATION_ERROR.getReasonPhrase()
		    	);
		}
		return respDTO;
	}

	private InquiryServiceResultDTO inquiryWebServiceException(InquiryServiceResultDTO respDTO, WebServiceException r) {
		// handle error from bank web service
		String faultString = r.getMessage();
		if(respDTO == null)
		{
		    if(faultString != null && (faultString.indexOf("java.net.SocketTimeoutException") > -1
		            || faultString.indexOf("Connection timed out") > -1 )) {
		        // bank timeout
		        	respDTO = new InquiryServiceResultDTO(
		            InquiryServiceStatus.ERROR_TIMEOUT.toString(),
		            InquiryServiceStatus.ERROR_TIMEOUT.getReasonPhrase()
		        );
		    } else {
		        // bank general error
		    		respDTO = new InquiryServiceResultDTO(
						InquiryServiceStatus.INTERNAL_APPLICATION_ERROR.toString(),
						InquiryServiceStatus.INTERNAL_APPLICATION_ERROR.getReasonPhrase()
		    		);
		    }
		}
		return respDTO;
	}

	private InquiryServiceResultDTO inquiryNullPointerExeption(InquiryServiceResultDTO respDTO) {
		if(respDTO == null) {
		    respDTO = new InquiryServiceResultDTO(
		    		InquiryServiceStatus.GENERAL_INVALID_DATA_500.toString(), 
		    		InquiryServiceStatus.GENERAL_INVALID_DATA_500.getReasonPhrase()
		    	);
		}
		return respDTO;
	}
    
}
