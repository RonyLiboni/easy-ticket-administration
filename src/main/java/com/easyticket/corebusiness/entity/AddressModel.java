package com.easyticket.corebusiness.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Addresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressModel {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 8)
	private String zipCode;
	
	@Column(nullable = false, length = 150)
	private String street;
	
	@Column(nullable = false)
	private Short streetNumber;
	
	@Column(nullable = false, length = 100)
	private String district;
	
	@Column(nullable = false, length = 100)
	private String city;
}
