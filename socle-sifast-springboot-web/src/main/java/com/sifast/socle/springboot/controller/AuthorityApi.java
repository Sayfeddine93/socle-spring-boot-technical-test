package com.sifast.socle.springboot.controller;

import java.util.Optional;

import javax.persistence.PersistenceException;

import com.sifast.socle.springboot.service.services.AuthorityService;
import com.sifast.socle.springboot.service.services.impl.AuthorityServiceImpl;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sifast.socle.springboot.dao.AuthorityDao;
import com.sifast.socle.springboot.model.Authority;
import com.sifast.socle.springboot.service.util.config.IValidatorError;
import com.sifast.socle.springboot.service.util.http.Constants;
import com.sifast.socle.springboot.service.util.http.HttpCostumCode;
import com.sifast.socle.springboot.service.util.http.HttpCostumMessage;
import com.sifast.socle.springboot.service.util.http.HttpErrorResponse;
import com.sifast.socle.springboot.service.util.http.HttpMessageResponse;
import com.sifast.socle.springboot.service.util.http.IAddWebServiceForUniqueConstraint;
import com.sifast.socle.springboot.service.util.http.IWebServicesValidators;

import static org.apache.naming.ResourceRef.AUTH;

@RestController
@RequestMapping(value = "/api/authorities", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
@Api(description = "authorities management")

public class AuthorityApi {

	@Autowired
	private AuthorityDao authorityDao;

	@Autowired
	private AuthorityService authorityService;


	private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

	// This attribut is added to return a not wrapped json object [only content
	// of the response without the field name]
	private Object httpResponseBody;

	private HttpStatus httpStatus;

	private ModelMapper modelMapper = new ModelMapper();

	public HttpErrorResponse getHttpErrorResponse() {
		return httpErrorResponse;
	}

	public void setHttpErrorResponse(HttpErrorResponse httpErrorResponse) {
		this.httpErrorResponse = httpErrorResponse;
	}

	public Object getHttpResponseBody() {
		return httpResponseBody;
	}

	public void setHttpResponseBody(Object httpResponseBody) {
		this.httpResponseBody = httpResponseBody;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	@ApiResponses(value = {
			@ApiResponse(code = Constants.STATUS_OK, message = "Get All authorities done", response = String.class),
			@ApiResponse(code = Constants.STATUS_NOT_FOUND, message = "No response data for this request", response = String.class),
			@ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = "Check your URL", response = String.class) })
	@ApiOperation(value = "Get all authorities")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {

		if (authorityService.findAll().isEmpty()) {
			httpStatus = HttpStatus.NOT_FOUND;
			httpResponseBody = httpErrorResponse;
		} else {
			httpStatus = HttpStatus.OK;
			httpResponseBody = authorityService.findAll();
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}
	
	

	@ApiResponses(value = {
			@ApiResponse(code = Constants.STATUS_OK, message = "Get authority by designation done", response = Authority.class),
			@ApiResponse(code = Constants.STATUS_NOT_FOUND, message = Constants.NO_RESPONSE, response = HttpErrorResponse.class),
			@ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
			@ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class) })
	@ApiOperation(value = "Find authority by designation", response = Authority.class, httpMethod = "GET")
	@RequestMapping(value = "/{designation}", method = RequestMethod.GET)
	public ResponseEntity<?> findAuthorityByDesignation(
			@ApiParam(required = true, value = "designation", name = "designation") @PathVariable("designation") String designation) {
		try {
			Authority auth = authorityService.findAuthorityByDesignation(designation);
			if (auth == null) {
				httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(),
						HttpCostumMessage.NO_RESPONSE_MSG.getValue());
				httpResponseBody = httpErrorResponse;
				httpStatus = HttpStatus.NOT_FOUND;
			} else {
				httpResponseBody = auth;
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception ex) {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(),
					HttpCostumMessage.BAD_ARGUMENTS.getValue());
			httpResponseBody = httpErrorResponse;
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@ApiResponses(value = {
			@ApiResponse(code = Constants.STATUS_CREATED, message = "authority Created successfully", response = Authority.class),
			@ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
			@ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class) })
	@ApiOperation(value = "Save authority", response = Authority.class)
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<?> save(
			@ApiParam(required = true, value = AUTH, name = AUTH) @Validated(value = { IWebServicesValidators.class,
					IAddWebServiceForUniqueConstraint.class }) @RequestBody Authority auth,
			BindingResult bindingResult) {
		if (!bindingResult.hasFieldErrors()) {
			Authority mappedAuth = auth;

			try {
				Authority savedAuth = authorityService.save(mappedAuth);
				httpStatus = HttpStatus.OK;
				httpResponseBody = modelMapper.map(savedAuth, Authority.class);
			} catch (PersistenceException ex) {
				httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(),
						IValidatorError.getValidatorErrors(bindingResult));
				httpResponseBody = httpErrorResponse;
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(),
					IValidatorError.getValidatorErrors(bindingResult));
			httpResponseBody = httpErrorResponse;
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@ApiResponses(value = {
			@ApiResponse(code = Constants.STATUS_OK, message = "authority deleted successfully", response = HttpMessageResponse.class),
			@ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
			@ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class) })
	@ApiOperation(value = "Delete authority", response = Authority.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(/*@ApiParam(required = true, value = "id", name = "id")*/ @PathVariable("id") int id) {

		Optional<Authority> preDeleteAuth = authorityService.findById(id);
		authorityService.findById(id);
		if (preDeleteAuth == null) {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(),
					"No Authority with the requested identifier, Check your entry please.");
			httpResponseBody = httpErrorResponse;
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			authorityService.deleteById(id);
			httpResponseBody = new HttpMessageResponse("authority deleted successfully");
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	
	
	@ApiResponses(value = {
			@ApiResponse(code = Constants.STATUS_OK, message = "authority updated successfully", response = Authority.class),
			@ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
			@ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class) })
	@ApiOperation(value = "Update authority", response = Authority.class)
	@PutMapping(value = "/")
	public ResponseEntity<?> update(@RequestBody Authority auth, BindingResult bindingResult) {
		try {
			httpStatus = HttpStatus.OK;
			httpResponseBody = authorityService.save(auth);
		} catch (PersistenceException ex) {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(),
					IValidatorError.getValidatorErrors(bindingResult));
			httpResponseBody = httpErrorResponse;
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
