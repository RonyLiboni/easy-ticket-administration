package com.easyticket.corebusiness.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NewCustomerModelRequest {
	
	@NotBlank
	@Length(max = 150)
	@Schema(example = "Ronald Liboni", description = "Here you have to add the name of the customer.")
	private String name;
	
	@NotBlank
	@Length(max = 100)
	@Schema(example = "RonaldLiboni@gmail.com", description = "Here you have to add the email of the customer.")
	private String email;
	
	@NotBlank
	@Length(min= 11 ,max = 14)
	@Schema(example = "339.874.880-50", description = "Here you have to add the CPF of the customer.")
	private String documentId;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	@Schema(example = "2022-12-12 08:00", description = "Here you have to add when the customer will happen.")
	private LocalDate birthDate;	
	
}
