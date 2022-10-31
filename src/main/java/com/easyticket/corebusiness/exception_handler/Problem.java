package com.easyticket.corebusiness.exception_handler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"status","type","title","detail","timestamp"})
@Getter
@Setter
@Builder
public class Problem {

	private Integer status;
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
	private String type;
	private String title;
	private String detail;
	private List<ObjectErrorsDto> objectErrors;
	
	public void setObjectErrors(List<ObjectErrorsDto> objectErrors) {
		this.objectErrors = objectErrors;
	}
}
