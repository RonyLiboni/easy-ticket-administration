package com.easyticket.corebusiness.exception_handler;

import org.springframework.validation.FieldError;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectErrorsDto {
	
	@Schema(example = "username")
	private String name;
	
	@Schema(example = "The username you input is in use, please try another one!")
	private String error;
	
	public ObjectErrorsDto (FieldError fieldError) {
		this.name = fieldError.getField();
		this.error = fieldError.getDefaultMessage();
	}	
}
