
package com.sifast.socle.springboot.controller;

import com.sifast.socle.springboot.model.Role;
import com.sifast.socle.springboot.service.services.impl.RoleServiceImpl;
import com.sifast.socle.springboot.service.util.config.IValidatorError;
import com.sifast.socle.springboot.service.util.http.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;

@RestController
@SessionAttributes("role")
@RequestMapping(value = "/api/roles", produces = {
    MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
@Api(description = "roles management")
public class RoleApi {

  private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

  // This attribut is added to return a not wrapped json object [only content
  // of the response without the field name]
  private Object httpResponseBody;

  private HttpStatus httpStatus;

  private ModelMapper modelMapper = new ModelMapper();

  private final RoleServiceImpl roleServiceImpl;

  @Autowired
  public RoleApi(RoleServiceImpl roleServiceImpl) {
    this.roleServiceImpl = roleServiceImpl;
  }

  @ApiResponses(value = {
      @ApiResponse(code = Constants.STATUS_OK, message = "Get All roles done", response = String.class),
      @ApiResponse(code = Constants.STATUS_NOT_FOUND, message = "No response data for this request", response = String.class),
      @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = "Check your URL", response = String.class)})
  @ApiOperation(value = "Get all roles")
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<?> findAll() {

    if (roleServiceImpl.findAll().isEmpty()) {

      httpStatus = HttpStatus.NOT_FOUND;
      httpResponseBody = httpErrorResponse;
    } else {
      httpStatus = HttpStatus.OK;
      httpResponseBody = roleServiceImpl.findAll();
    }
    return new ResponseEntity<>(httpResponseBody, httpStatus);
  }

  @ApiResponses(value = {
      @ApiResponse(code = Constants.STATUS_OK, message = "Get role by designation done", response = Role.class),
      @ApiResponse(code = Constants.STATUS_NOT_FOUND, message = Constants.NO_RESPONSE, response = HttpErrorResponse.class),
      @ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
      @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class)})
  @ApiOperation(value = "Find role by designation", response = Role.class, httpMethod = "GET")
  @RequestMapping(value = "/{designation}", method = RequestMethod.GET)
  public ResponseEntity<?> findRoleByDesignation(
      /*
       * @ApiParam(required = true, value = "designation", name = "designation")
       */@PathVariable("designation") String designation) {
    try {
      Role role = roleServiceImpl.findRoleByDesignation(designation);
      if (role == null) {
        httpErrorResponse.setHttpCodeAndMessage(
            HttpCostumCode.NOT_FOUND.getValue(),
            HttpCostumMessage.NO_RESPONSE_MSG.getValue());
        httpResponseBody = httpErrorResponse;
        httpStatus = HttpStatus.NOT_FOUND;
      } else {

        httpResponseBody = role;
        httpStatus = HttpStatus.OK;
      }
    } catch (Exception ex) {
      httpErrorResponse.setHttpCodeAndMessage(
          HttpCostumCode.NOT_FOUND.getValue(),
          HttpCostumMessage.BAD_ARGUMENTS.getValue());
      httpResponseBody = httpErrorResponse;
      httpStatus = HttpStatus.BAD_REQUEST;
    }
    return new ResponseEntity<>(httpResponseBody, httpStatus);
  }

  @ApiResponses(value = {
      @ApiResponse(code = Constants.STATUS_CREATED, message = "role Created successfully", response = Role.class),
      @ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
      @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class)})
  @ApiOperation(value = "Save role", response = Role.class)
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> save(/*
   * @ApiParam(required = true, value = ROLE, name
   * = ROLE)
   */@Validated(value = {
      IWebServicesValidators.class,
      IAddWebServiceForUniqueConstraint.class}) @RequestBody Role role,
      BindingResult bindingResult) {
    if (!bindingResult.hasFieldErrors()) {
      Role mappedUser = role;

      try {
        Role savedRole = roleServiceImpl.save(mappedUser);
        httpStatus = HttpStatus.OK;
        httpResponseBody = modelMapper.map(savedRole, Role.class);
      } catch (PersistenceException ex) {
        httpErrorResponse.setHttpCodeAndMessage(
            HttpCostumCode.SERVER_ERROR.getValue(),
            IValidatorError.getValidatorErrors(bindingResult));
        httpResponseBody = httpErrorResponse;
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      }
    } else {
      httpErrorResponse.setHttpCodeAndMessage(
          HttpCostumCode.BAD_REQUEST.getValue(),
          IValidatorError.getValidatorErrors(bindingResult));
      httpResponseBody = httpErrorResponse;
      httpStatus = HttpStatus.BAD_REQUEST;
    }
    return new ResponseEntity<>(httpResponseBody, httpStatus);
  }

  @ApiResponses(value = {
      @ApiResponse(code = Constants.STATUS_OK, message = "role deleted successfully", response = HttpMessageResponse.class),
      @ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
      @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class)})
  @ApiOperation(value = "Delete role", response = Role.class)
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> delete(/*
   * @ApiParam(required = true, value = "id",
   * name = "id")
   */@PathVariable("id") int id) {

    Role preDeleteUser = roleServiceImpl.findById(id);

    roleServiceImpl.findById(id);
    if (preDeleteUser == null) {
      httpErrorResponse
          .setHttpCodeAndMessage(
              HttpCostumCode.BAD_REQUEST.getValue(),
              "No Role with the requested identifier, Check your entry please.");
      httpResponseBody = httpErrorResponse;
      httpStatus = HttpStatus.BAD_REQUEST;
    } else {
      roleServiceImpl.deleteById(id);
      httpResponseBody = new HttpMessageResponse(
          "Role deleted successfully");
      httpStatus = HttpStatus.OK;
    }
    return new ResponseEntity<>(httpResponseBody, httpStatus);
  }

  @ApiResponses(value = {
      @ApiResponse(code = Constants.STATUS_OK, message = "role updated successfully", response = Role.class),
      @ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
      @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class)})
  @ApiOperation(value = "Update role", response = Role.class)
  @PutMapping(value = "/")
  public ResponseEntity<?> update(@RequestBody Role role,
      BindingResult bindingResult) {

    try {
      httpStatus = HttpStatus.OK;
      httpResponseBody = roleServiceImpl.save(role);
    } catch (PersistenceException ex) {
      httpErrorResponse.setHttpCodeAndMessage(
          HttpCostumCode.SERVER_ERROR.getValue(),
          IValidatorError.getValidatorErrors(bindingResult));
      httpResponseBody = httpErrorResponse;
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return new ResponseEntity<>(httpResponseBody, httpStatus);

  }

}
