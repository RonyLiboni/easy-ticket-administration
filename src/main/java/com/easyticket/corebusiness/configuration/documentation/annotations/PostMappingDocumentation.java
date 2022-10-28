package com.easyticket.corebusiness.configuration.documentation.annotations;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(security = { @SecurityRequirement(name = "bearer-key") },
	responses = {@ApiResponse(responseCode= "201", description = "The resource was created with success!")})
public @interface PostMappingDocumentation {
	String summary();
}
