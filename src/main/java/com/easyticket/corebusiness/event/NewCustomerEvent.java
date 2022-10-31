package com.easyticket.corebusiness.event;

import com.easyticket.corebusiness.dto.NewCustomerModelRequest;

import lombok.Getter;

@Getter
public class NewCustomerEvent {
	
	private NewCustomerModelRequest newCustomer;
	
	public NewCustomerEvent(NewCustomerModelRequest newCustomer) {
		this.newCustomer = newCustomer;
	}
	
	public String getEmail() {
		return newCustomer.getEmail();
	}
	
	public String getPassword() {
		return newCustomer.getPassword();
	}
}
