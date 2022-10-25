package com.easyticket.corebusiness.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.easyticket.corebusiness.entity.AddressModel;
import com.easyticket.corebusiness.http.ViaCepAddress;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NewAddressModelRequest {
	
	@NotBlank
	@Length(max = 100)
	@Schema(example = "Ginásio Gigantinho", description = "Here you have to add the name of the place the show will happen.")
	private String name;
	@Length(min = 8, max = 9)
	@Schema(example = "90810-240")
	private String zipCode;
	@Min(1)
	@Max(9999)
	@Schema(example = "891", minimum = "1", maximum = "9999")
	private Short streetNumber;
	
	public AddressModel toEntity(String zipCode, ViaCepAddress viaCep) {
		return AddressModel.builder()
							.name(this.name)
							.zipCode(zipCode)
							.street(viaCep.getLogradouro())
							.streetNumber(this.streetNumber)
							.district(viaCep.getBairro())
							.city(viaCep.getLocalidade())									
							.build();
	}
}
