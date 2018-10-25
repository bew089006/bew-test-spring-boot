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
import com.ascendcorp.exam.proxy.action.ResponseBankInvalidData;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ResponseBankInvalidDataTest {

    @Mock
    BankProxyGateway bankProxyGateway;

    @Test
    public void should_return400_when_invalidDataWithoutDesc() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("invalid_data");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankInvalidData().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("400", inquiry.getReasonCode());
        assertEquals("General Invalid Data", inquiry.getReasonDesc());
    }


    @Test
    public void should_return400_when_invalidDataWithDesc() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("invalid_data");
        transferResponse.setDescription("General error.");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankInvalidData().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("400", inquiry.getReasonCode());
        assertEquals("General Invalid Data", inquiry.getReasonDesc());
    }

}
