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
import com.ascendcorp.exam.proxy.action.ResponseBankApproved;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ResponseBankApprovedTest {

    @Mock
    BankProxyGateway bankProxyGateway;

    @Test
    public void should_return200_when_bankApproved() throws SQLException {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setResponseCode("approved");
        transferResponse.setDescription("approved");

        when(bankProxyGateway.requestTransfer(anyString(),any(),anyString(),anyString(),anyString(),
                anyDouble(),anyString(),anyString())).thenReturn(transferResponse);

        InquiryServiceResultDTO inquiry = new InquiryServiceResultDTO();
        new ResponseBankApproved().processManual(transferResponse, inquiry);

        assertNotNull(inquiry);
        assertEquals("200", inquiry.getReasonCode());
        assertEquals("approved", inquiry.getReasonDesc());
    }
}
