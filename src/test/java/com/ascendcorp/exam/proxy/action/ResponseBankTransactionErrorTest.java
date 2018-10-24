package com.ascendcorp.exam.proxy.action;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import com.ascendcorp.exam.proxy.BankProxyGateway;
import com.ascendcorp.exam.proxy.action.ResponseBankTransactionError;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ResponseBankTransactionErrorTest {

    @Mock
    BankProxyGateway bankProxyGateway;

    @Test
    public void should_return400_when_errorAndDescIsNull() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("transaction_error");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankTransactionError().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("500", inquiry.getReasonCode());
        assertEquals("General Transaction Error", inquiry.getReasonDesc());
    }

    @Test
    public void should_return400_when_errorAndNoDescCode() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("transaction_error");
        transferResponse.setDescription("Transaction error.");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankTransactionError().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("500", inquiry.getReasonCode());
        assertEquals("General Transaction Error", inquiry.getReasonDesc());
    }

    @Test
    public void should_return400_when_errorAndDesc3Code() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("transaction_error");
        transferResponse.setDescription("100:1091:Transaction is error with code 1091.");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankTransactionError().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("1091", inquiry.getReasonCode());
        assertEquals("Transaction is error with code 1091.", inquiry.getReasonDesc());
    }

    @Test
    public void should_return400_when_errorAndDesc2Code() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("transaction_error");
        transferResponse.setDescription("1092:Transaction is error with code 1092.");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankTransactionError().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("1092", inquiry.getReasonCode());
        assertEquals("Transaction is error with code 1092.", inquiry.getReasonDesc());
    }

    @Test
    public void should_return400_when_errorAndDescCode98() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("transaction_error");
        transferResponse.setDescription("98:Transaction is error with code 98.");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankTransactionError().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("98", inquiry.getReasonCode());
        assertEquals("Transaction is error with code 98.", inquiry.getReasonDesc());
    }
    
}
