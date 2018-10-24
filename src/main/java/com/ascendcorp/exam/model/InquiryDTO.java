package com.ascendcorp.exam.model;

import java.io.Serializable;
import java.util.Date;

public class InquiryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String transactionId;
	private Date tranDateTime;
    private String channel;
    private String locationCode;
    private String bankCode;
    private String bankNumber;
    private double amount = 0;
    private String reference1;
    private String reference2;
    private String firstName;
    private String lastName;
    
	public InquiryDTO(String transactionId, Date tranDateTime, String channel, String locationCode, String bankCode, String bankNumber,
			double amount, String reference1, String reference2, String firstName, String lastName) {
		super();
		this.transactionId = transactionId;
		this.tranDateTime = tranDateTime;
		this.channel = channel;
		this.locationCode = locationCode;
		this.bankCode = bankCode;
		this.bankNumber = bankNumber;
		this.amount = amount;
		this.reference1 = reference1;
		this.reference2 = reference2;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTranDateTime() {
		return tranDateTime;
	}
	public void setTranDateTime(Date tranDateTime) {
		this.tranDateTime = tranDateTime;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getReference1() {
		return reference1;
	}
	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}
	public String getReference2() {
		return reference2;
	}
	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
