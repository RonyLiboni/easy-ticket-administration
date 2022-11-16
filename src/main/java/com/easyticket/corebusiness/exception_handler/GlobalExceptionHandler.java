package com.easyticket.corebusiness.exception_handler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.easyticket.corebusiness.exception.EntityInUseException;
import com.easyticket.corebusiness.exception.RequestToOtherMicroserviceFailedException;
import com.easyticket.corebusiness.exception.StorageException;
import com.easyticket.corebusiness.exception.ZipCodeNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	public static final String GENERIC_MESSAGE_FOR_USER	= "An unexpected error happened in the system. "
														+ "Try again and if the error remain, please, "
														+ "contact a system administrator.";
	
	private final MessageSource messageSource;
	
	@ExceptionHandler(StorageException.class)
	@ApiResponse(responseCode= "500", description = "An unexpected error happened in the server side!")
	public ResponseEntity<Object> handleStorageException(StorageException exception, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Problem body = buildBody(exception.getMessage(), GENERIC_MESSAGE_FOR_USER, status, ProblemType.FILE_STORAGE_FAILED);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityInUseException.class)
	@ApiResponse(responseCode= "409", description = "There was a conflict in your request!")
	public ResponseEntity<Object> handleEntityInUseException(EntityInUseException exception, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		Problem body = buildBody(exception.getMessage(), null, status, ProblemType.ENTITY_IN_USE);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(RequestToOtherMicroserviceFailedException.class)
	@ApiResponse(responseCode= "400", description = "You've made something wrong!")
	public ResponseEntity<Object> handleRequestToOtherMicroserviceFailedException(RequestToOtherMicroserviceFailedException exception, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		var problemType = ProblemType.REQUEST_TO_OTHER_MICROSERVICE_FAILED;
		
		if (exception.getErrorMessage() == null || exception.getErrorMessage().equals("") ) {
			Problem body = buildBody(exception.getDetailMessage(), GENERIC_MESSAGE_FOR_USER, status, problemType);
			return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
		}
		Problem body = buildBody(exception.getErrorMessage(), null, status, problemType);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, BindingResult bindingResult) {

		String detail = "One or more fields are invalid. Please fill the form with valid data and try again.";
		List<ObjectErrorsDto> validationErrors = 
	    		bindingResult.getAllErrors().stream()
		    		.map(objectError -> {
		    			String validatonErrorMessage = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
		    			String name = objectError.getObjectName();
		    			
		    			if (objectError instanceof FieldError) {
		    				name = ((FieldError) objectError).getField();
		    			}
		    			return new ObjectErrorsDto(name, validatonErrorMessage);
		    		})
		    		.collect(Collectors.toList());
		Problem body = buildBody(detail, null, status, ProblemType.OBJECT_WITH_VALIDATION_ERROR, validationErrors);

	    
	    return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ApiResponse(responseCode= "404", description = "Resource not found!")
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problem body = buildBody(exception.getMessage(), null, status, ProblemType.RESOURCE_NOT_FOUND);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler({ZipCodeNotFoundException.class, MultipartException.class})
	@ApiResponse(responseCode= "400", description = "You've made something wrong!")
	public ResponseEntity<Object> handleZipCodeNotFoundException(Exception exception, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problem body = buildBody(exception.getMessage(), null, status, ProblemType.OBJECT_WITH_VALIDATION_ERROR);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(Exception.class)
	@ApiResponse(responseCode= "500", description = "An unexpected error happened in the server side!")
	public ResponseEntity<Object> handleUncaughtExceptions(Exception exception, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Problem body = buildBody(GENERIC_MESSAGE_FOR_USER, null, status, ProblemType.INTERNAL_SERVER_ERROR);
		return handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
	}
	
	private Problem buildBody(String detail, String userMessage, HttpStatus status, ProblemType problemType) {
		return Problem.builder()
						.status(status.value())
						.type(problemType.getUri())
						.title(problemType.getTitle())
						.detail(detail)
						.userMessage(userMessage)
						.build();
	}
	
	private Problem buildBody(String detail, String userMessage, HttpStatus status, ProblemType problemType, List<ObjectErrorsDto> validationErrors) {
		Problem body = buildBody(detail,userMessage, status, problemType);
		body.setObjectErrors(validationErrors);		
		return body;
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = Problem.builder()
					.timestamp(OffsetDateTime.now())
					.title(status.getReasonPhrase())
					.status(status.value())
					.userMessage(GENERIC_MESSAGE_FOR_USER)
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.timestamp(OffsetDateTime.now())
					.title((String) body)
					.status(status.value())
					.userMessage(GENERIC_MESSAGE_FOR_USER)
					.build();
		}		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request); 
		}
		
		Problem body = buildBody(ex.getRootCause().getMessage(), GENERIC_MESSAGE_FOR_USER, status, ProblemType.MESSAGE_NOT_READABLE);
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		String detail = String.format("The property '%s' does not exist. "
				+ "Correct or remove that property and try again.", getFieldName(ex.getPath()));
		
		Problem body = buildBody(detail,GENERIC_MESSAGE_FOR_USER, status, ProblemType.MESSAGE_NOT_READABLE);
		
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String detail = String.format("The property '%s' got the value '%s', "
				+ "that is an invalid type. Inform a compatible value with the type '%s'.",
				getFieldName(ex.getPath()), ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem body = buildBody(detail,GENERIC_MESSAGE_FOR_USER, status, ProblemType.MESSAGE_NOT_READABLE);
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private String getFieldName(List<Reference> references) {
		return references.stream()
							.map(ref -> ref.getFieldName())
							.collect(Collectors.joining("."));
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String detail =String.format("The resource '%s', that you tried to access, does not exist!", ex.getRequestURL());
		Problem body = buildBody(detail, GENERIC_MESSAGE_FOR_USER ,status, ProblemType.RESOURCE_NOT_FOUND);
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(
					(MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
	
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
			MethodArgumentTypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String detail = String.format("The URL parameter got the value '%s', "
				+ "that is an invalid type. Please inform a value compatible with the type '%s'.",
				ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem body = buildBody(detail, GENERIC_MESSAGE_FOR_USER ,status, ProblemType.INVALID_PARAMETER);

		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {	
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}
			
}
