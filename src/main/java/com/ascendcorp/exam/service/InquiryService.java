package com.ascendcorp.exam.service;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import com.ascendcorp.exam.proxy.BankProxyGateway;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.WebServiceException;
import java.util.Date;

public class InquiryService {

    @Autowired
    private BankProxyGateway bankProxyGateway;

    final static Logger log = Logger.getLogger(InquiryService.class);

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
        try
        {
            log.info("validate request parameters.");
            if(transactionId == null) {
                log.info("Transaction id is required!");
                throw new NullPointerException("Transaction id is required!");
            }
            if(tranDateTime == null) {
                log.info("Transaction DateTime is required!");
                throw new NullPointerException("Transaction DateTime is required!");
            }
            if(channel == null) {
                log.info("Channel is required!");
                throw new NullPointerException("Channel is required!");
            }
            if(bankCode == null || bankCode.equalsIgnoreCase("")) {
                log.info("Bank Code is required!");
                throw new NullPointerException("Bank Code is required!");
            }
            if(bankNumber == null || bankNumber.equalsIgnoreCase("")) {
                log.info("Bank Number is required!");
                throw new NullPointerException("Bank Number is required!");
            }
            if(amount <= 0) {
                log.info("Amount must more than zero!");
                throw new NullPointerException("Amount must more than zero!");
            }

            log.info("call bank web service");
            TransferResponse response = bankProxyGateway.requestTransfer(transactionId, tranDateTime, channel,
                    bankCode, bankNumber, amount, reference1, reference2);

            log.info("check bank response code");
            if(response != null) //New
            {
                if(response.getResponseCode()==null) {
                    // bank code is null
                    throw new Exception("No response code");
                }
                log.debug("found response code");
                respDTO = new InquiryServiceResultDTO();

                respDTO.setRef_no1(response.getReferenceCode1());
                respDTO.setRef_no2(response.getReferenceCode2());
                respDTO.setAmount(response.getBalance());
                respDTO.setTranID(response.getBankTransactionID());

                String replyDesc;
                switch(response.getResponseCode().toLowerCase()) {
                    case "approved" :
                        // bank response code = approved
                        respDTO.setReasonCode("200");
                        respDTO.setReasonDesc(response.getDescription());
                        respDTO.setAccountName(response.getDescription());
                        break;
                    case "invalid_data" :
                        // bank response code = invalid_data
                        replyDesc = response.getDescription();
                        if(replyDesc != null)
                        {
                            String respDesc[] = replyDesc.split(":");
                            if(respDesc.length >= 3)
                            {
                                // bank description full format
                                respDTO.setReasonCode(respDesc[1]);
                                respDTO.setReasonDesc(respDesc[2]);
                            }else
                            {
                                // bank description short format
                                respDTO.setReasonCode("400");
                                respDTO.setReasonDesc("General Invalid Data");
                            }
                        }else
                        {
                            // bank no description
                            respDTO.setReasonCode("400");
                            respDTO.setReasonDesc("General Invalid Data");
                        }
                        break;
                    case "transaction_error" :
                        // bank response code = transaction_error
                        replyDesc = response.getDescription();
                        if(replyDesc != null)
                        {
                            String respDesc[] = replyDesc.split(":");
                            if(respDesc.length >= 2)
                            {
                                log.info("Case Inquiry Error Code Format Now Will Get From [0] and [1] first");
                                String subIdx1 = respDesc[0];
                                String subIdx2 = respDesc[1];
                                log.info("index[0] : "+subIdx1 + " index[1] is >> "+subIdx2);
                                if("98".equalsIgnoreCase(subIdx1))
                                {
                                    // bank code 98
                                    respDTO.setReasonCode(subIdx1);
                                    respDTO.setReasonDesc(subIdx2);
                                }else
                                {
                                    log.info("case error is not 98 code");
                                    if(respDesc.length >= 3)
                                    {
                                        // bank description full format
                                        String subIdx3 = respDesc[2];
                                        log.info("index[2] : "+subIdx3);
                                        respDTO.setReasonCode(subIdx2);
                                        respDTO.setReasonDesc(subIdx3);
                                    }else
                                    {
                                        // bank description short format
                                        respDTO.setReasonCode(subIdx1);
                                        respDTO.setReasonDesc(subIdx2);
                                    }
                                }
                            }else
                            {
                                // bank description incorrect format
                                respDTO.setReasonCode("500");
                                respDTO.setReasonDesc("General Transaction Error");
                            }
                        }else
                        {
                            // bank no description
                            respDTO.setReasonCode("500");
                            respDTO.setReasonDesc("General Transaction Error");
                        }
                        break;
                    case "unknown" :
                        replyDesc = response.getDescription();
                        if(replyDesc != null)
                        {
                            String respDesc[] = replyDesc.split(":");
                            if(respDesc.length >= 2)
                            {
                                // bank description full format
                                respDTO.setReasonCode(respDesc[0]);
                                respDTO.setReasonDesc(respDesc[1]);
                                if(respDTO.getReasonDesc() == null || respDTO.getReasonDesc().trim().length() == 0)
                                {
                                    respDTO.setReasonDesc("General Invalid Data");
                                }
                            }else
                            {
                                // bank description short format
                                respDTO.setReasonCode("501");
                                respDTO.setReasonDesc("General Invalid Data");
                            }
                        }else
                        {
                            // bank no description
                            respDTO.setReasonCode("501");
                            respDTO.setReasonDesc("General Invalid Data");
                        }
                        break;
                    default :
                        // bank code not support
                        throw new Exception("Unsupport Error Reason Code");
                }
            }else{
                // no resport from bank
                throw new Exception("Unable to inquiry from service.");
            }
        }catch(NullPointerException ne)
        {
            if(respDTO == null)
            {
                respDTO = new InquiryServiceResultDTO();
                respDTO.setReasonCode("500");
                respDTO.setReasonDesc("General Invalid Data");
            }
        }catch(WebServiceException wse)
        {
            // handle error from bank web service
            String faultString = wse.getMessage();
            if(respDTO == null)
            {
                respDTO = new InquiryServiceResultDTO();
                if(faultString != null && (faultString.contains("java.net.SocketTimeoutException")
                        || faultString.contains("Connection timed out") ))
                {
                    // bank timeout
                    respDTO.setReasonCode("503");
                    respDTO.setReasonDesc("Error timeout");
                }else
                {
                    // bank general error
                    respDTO.setReasonCode("504");
                    respDTO.setReasonDesc("Internal Application Error");
                }
            }
        }
        catch(Exception e)
        {
            log.error("inquiry exception", e);
            if(respDTO == null || respDTO.getReasonCode() == null)
            {
                respDTO = new InquiryServiceResultDTO();
                respDTO.setReasonCode("504");
                respDTO.setReasonDesc("Internal Application Error");
            }
        }
        return respDTO;
    }
}
