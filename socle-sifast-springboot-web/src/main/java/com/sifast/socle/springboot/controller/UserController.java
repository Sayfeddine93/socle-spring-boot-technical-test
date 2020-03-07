package com.sifast.socle.springboot.controller;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sifast.socle.springboot.dao.UserDao;
import com.sifast.socle.springboot.model.User;
import com.sifast.socle.springboot.service.services.UserService;
import com.sifast.socle.springboot.service.util.config.IValidatorError;
import com.sifast.socle.springboot.service.util.http.Constants;
import com.sifast.socle.springboot.service.util.http.HttpCostumCode;
import com.sifast.socle.springboot.service.util.http.HttpCostumMessage;
import com.sifast.socle.springboot.service.util.http.HttpErrorResponse;
import com.sifast.socle.springboot.service.util.http.HttpMessageResponse;
import com.sifast.socle.springboot.service.util.http.IAddWebServiceForUniqueConstraint;
import com.sifast.socle.springboot.service.util.http.IWebServicesValidators;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@SessionAttributes("user")
@RequestMapping(value = "/api/users", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
@Api(description = "Users management")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

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

    @ApiResponses(value = { @ApiResponse(code = Constants.STATUS_OK, message = "Get All users done", response = String.class),
            @ApiResponse(code = Constants.STATUS_NOT_FOUND, message = "No response data for this request", response = String.class),
            @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = "Check your URL", response = String.class) })
    @ApiOperation(value = "Get all users")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {

        if (userService.findAll().isEmpty()) {

            httpStatus = HttpStatus.NOT_FOUND;
            httpResponseBody = httpErrorResponse;
        } else {
            httpStatus = HttpStatus.OK;
            httpResponseBody = userService.findAll();
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @ApiResponses(value = { @ApiResponse(code = Constants.STATUS_OK, message = "Get user by username done", response = User.class),
            @ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
            @ApiResponse(code = Constants.STATUS_NOT_FOUND, message = Constants.NO_RESPONSE, response = HttpErrorResponse.class),
            @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Find user by username", response = User.class)
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> findByUsername(/* @ApiParam (required = true, value = "username", name = "username") */@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        try {
            if (user == null) {
                httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), "No User found for the requested username");
                httpStatus = HttpStatus.NOT_FOUND;
                httpResponseBody = httpErrorResponse;
            } else {
                httpStatus = HttpStatus.OK;
                httpResponseBody = user;
            }
        } catch (Exception ex) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(), HttpCostumMessage.BAD_ARGUMENTS.getValue());
            httpResponseBody = httpErrorResponse;
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);

    }

    @ApiResponses(value = { @ApiResponse(code = Constants.STATUS_CREATED, message = "User Created successfully", response = User.class),
            @ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
            @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Save user", response = User.class)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(/* @ApiParam(required = true, value = USER, name = USER) */@Validated(value = { IWebServicesValidators.class,
            IAddWebServiceForUniqueConstraint.class }) @RequestBody User user, BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            User mappedUser = user;
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            mappedUser.setPassword(encoder.encode(user.getPassword()));

            try {
                User savedUser = userService.save(mappedUser);
                httpStatus = HttpStatus.OK;
                httpResponseBody = modelMapper.map(savedUser, User.class);
            } catch (PersistenceException ex) {
                httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), IValidatorError.getValidatorErrors(bindingResult));
                httpResponseBody = httpErrorResponse;
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(), IValidatorError.getValidatorErrors(bindingResult));
            httpResponseBody = httpErrorResponse;
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @ApiResponses(value = { @ApiResponse(code = Constants.STATUS_OK, message = "User deleted successfully", response = HttpMessageResponse.class),
            @ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
            @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Delete user", response = User.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(/* @ApiParam(required = true, value ="id", name ="id") */@PathVariable("id") int id) {

        Optional<User> preDeleteUser = userService.findById(id);

        userService.findById(id);
        if (preDeleteUser == null) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.BAD_REQUEST.getValue(), "No User with the requested identifier, Check your entry please.");
            httpResponseBody = httpErrorResponse;
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            userService.deleteUserById(id);
            httpResponseBody = new HttpMessageResponse("User deleted successfully");
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @ApiResponses(value = { @ApiResponse(code = Constants.STATUS_OK, message = "User updated successfully", response = User.class),
            @ApiResponse(code = Constants.STATUS_SERVER_ERROR, message = Constants.CHECK_ENTRY, response = HttpErrorResponse.class),
            @ApiResponse(code = Constants.STATUS_BAD_REQUEST, message = Constants.CHECK_URL, response = HttpErrorResponse.class) })
    @ApiOperation(value = "Update user", response = User.class)
    @PutMapping(value = "/")
    public ResponseEntity<?> update(@RequestBody User user, BindingResult bindingResult) {

        try {
            httpStatus = HttpStatus.OK;
            httpResponseBody = userService.save(user);

        } catch (PersistenceException ex) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), IValidatorError.getValidatorErrors(bindingResult));
            httpResponseBody = httpErrorResponse;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(httpResponseBody, httpStatus);

    }

}
