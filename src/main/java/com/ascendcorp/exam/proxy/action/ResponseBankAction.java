package com.ascendcorp.exam.proxy.action;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;

public interface ResponseBankAction {
	void processManual(TransferResponse transferResponse, InquiryServiceResultDTO respDTO);
}
