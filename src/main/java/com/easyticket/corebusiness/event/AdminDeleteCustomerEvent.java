package com.easyticket.corebusiness.event;

import lombok.Getter;

@Getter
public class AdminDeleteCustomerEvent {
	
	private final String username;
	
	public AdminDeleteCustomerEvent(String username) {
		this.username = username;
	}
	
}
