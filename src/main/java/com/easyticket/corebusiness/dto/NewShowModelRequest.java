package com.easyticket.corebusiness.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NewShowModelRequest {
	
	@NotBlank
	@Length(max = 100)
	@Schema(example = "Os Barbixas", description = "Here you have to add the name of the show.")
	private String name;
	
	@NotNull
	@Digits(fraction = 2, integer = 10)
	@Schema(example = "95.00", description = "Here you have to add the ticket price for the show.")
	private BigDecimal price;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Future
	@Schema(example = "2022-12-12 08:00", description = "Here you have to add when the show will happen.")
	private LocalDateTime showDate;
	
	@NotNull
	@Positive
	@Schema(example = "3000", description = "Here you have to add how many tickets can be sold.")
	private Integer ticketCapacity;
	
	@Valid
	@NotNull
	private AddressModelId addressId;
	
}
