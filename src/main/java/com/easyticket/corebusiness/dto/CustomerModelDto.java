package com.easyticket.corebusiness.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerModelDto {
	
	private String name;
	private LocalDate birthDate;
	private String documentId;
	private String email;
	
}
