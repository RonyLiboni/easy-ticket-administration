package com.easyticket.corebusiness.exception_handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObjectErrorsDto {
	
	@Schema(example = "username")
	private String name;
	
	@Schema(example = "The username you input is in use, please try another one!")
	private String userMessage;
	
}
