package com.easyticket.corebusiness.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class ShowModelDto {
	
	private String name;
	private BigDecimal price;
	private LocalDateTime showDate;
	private Integer ticketCapacity;
	private AddressModelDto addressModelDto;
		
}
