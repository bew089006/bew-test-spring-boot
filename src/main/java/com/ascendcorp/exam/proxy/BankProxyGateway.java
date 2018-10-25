package com.ascendcorp.exam.proxy;

import java.util.Date;

import com.ascendcorp.exam.model.TransferResponse;


public class BankProxyGateway {

    public TransferResponse requestTransfer(
            String transactionId, Date tranDateTime, String channel,
            String bankCode, String bankNumber, double amount,
            String reference1, String reference2) {

        return new TransferResponse();
    }
}

