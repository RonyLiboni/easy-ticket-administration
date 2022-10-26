package com.easyticket.corebusiness.dto;

import com.easyticket.corebusiness.entity.AddressModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressModelDto {
	
	private String name;
	private String zipCode;
	private String street;
	private Short streetNumber;
	private String district;
	private String city;
	
	public AddressModelDto (AddressModel address) {
		this.name = address.getName();
		this.zipCode = address.getZipCode();
		this.city = address.getCity();
		this.district = address.getDistrict();
		this.street = address.getStreet();
		this.streetNumber = address.getStreetNumber();
	}
}
