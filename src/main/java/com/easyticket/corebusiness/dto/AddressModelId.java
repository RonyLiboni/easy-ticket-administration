package com.easyticket.corebusiness.dto;

import javax.validation.constraints.NotNull;

import com.easyticket.corebusiness.entity.AddressModel;
import com.easyticket.corebusiness.validation.exist_in_db.should_exist_in_db.ShouldExistInDataBase;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModelId {
	
	@ShouldExistInDataBase(entity = AddressModel.class, field = "id")
	@NotNull
	@Schema(example = "1")
	private Long addressId;
}
