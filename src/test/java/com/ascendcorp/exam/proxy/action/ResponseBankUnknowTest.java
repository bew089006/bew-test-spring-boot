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
import com.ascendcorp.exam.proxy.action.ResponseBankUnknow;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ResponseBankUnknowTest {

    @Mock
    BankProxyGateway bankProxyGateway;

    @Test
    public void should_return501_when_unknownAndWithoutDesc() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("unknown");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankUnknow().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("501", inquiry.getReasonCode());
        assertEquals("General Invalid Data", inquiry.getReasonDesc());
    }

    @Test
    public void should_return501_when_unknownAndDesc() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("unknown");
        transferResponse.setDescription("5001:Unknown error code 5001");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankUnknow().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("5001", inquiry.getReasonCode());
        assertEquals("Unknown error code 5001", inquiry.getReasonDesc());
    }

    @Test
    public void should_return501_when_unknownAndEmptyDesc() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("unknown");
        transferResponse.setDescription("5002: ");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankUnknow().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("5002", inquiry.getReasonCode());
        assertEquals("General Invalid Data", inquiry.getReasonDesc());
    }

    @Test
    public void should_return501_when_unknownAndTextDesc() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("unknown");
        transferResponse.setDescription("General Invalid Data code 501");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankUnknow().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("501", inquiry.getReasonCode());
        assertEquals("General Invalid Data", inquiry.getReasonDesc());
    }

}
