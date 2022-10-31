package com.easyticket.corebusiness.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.easyticket.corebusiness.entity.CustomerModel;
import com.easyticket.corebusiness.validation.document_id.DocumentIdIsValid;
import com.easyticket.corebusiness.validation.exist_in_db.should_not_exist_in_db.ShouldNotExistInDataBase;

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
	@ShouldNotExistInDataBase(entity = CustomerModel.class, field = "email")
	@Schema(example = "RonaldLiboni@gmail.com", description = "Here you have to add the email of the customer.")
	private String email;
	
	@DocumentIdIsValid
	@ShouldNotExistInDataBase(entity = CustomerModel.class, field = "documentId")
	@Schema(example = "339.874.880-50", description = "Here you have to add the CPF of the customer.")
	private String documentId;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	@Schema(example = "2022-12-12", description = "Here you have to add when the customer will happen.")
	private LocalDate birthDate;
	
	@NotBlank
	@Length(min = 8, max = 30)
	@Schema(example = "Easy_p4ss", description = "here you have to add a strong password.")
	private String password;
	
}
