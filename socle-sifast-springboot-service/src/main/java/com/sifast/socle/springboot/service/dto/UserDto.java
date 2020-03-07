package com.sifast.socle.springboot.service.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sifast.socle.springboot.service.annotations.Unique;
import com.sifast.socle.springboot.model.User;
import com.sifast.socle.springboot.service.util.http.IAddWebServiceForUniqueConstraint;
import com.sifast.socle.springboot.service.util.http.IWebServicesValidators;
import com.sifast.socle.springboot.service.util.http.Constants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * UserDto class
 */
@ApiModel(description = "User")
public class UserDto {

    @ApiModelProperty(value = "User identifier")
    private int id;

    @Pattern(regexp = Constants.NOT_BLANK_REGEX, message = "First Name can't be empty",groups = IWebServicesValidators.class)
    @Size(min = 3, max = 20, message = "First Name value '${validatedValue}' must be between {min} and {max} characters long", groups = IWebServicesValidators.class)
    @ApiModelProperty(required = true, value = "User's first Name")
    private String firstname;

    @Pattern(regexp = Constants.NOT_BLANK_REGEX, message = "Last Name can't be empty",groups = IWebServicesValidators.class)
    @Size(min = 3, max = 20, message = "Last Name value '${validatedValue}' must be between {min} and {max} characters long", groups = IWebServicesValidators.class)
    @ApiModelProperty(required = true, value = "User's last Name")
    private String lastname;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Check your Email please",groups = IWebServicesValidators.class)
    @ApiModelProperty(required = true, value = "User's email")
    @Unique(className = User.class, attributName = "email", message = "Email ${validatedValue} already exist", groups = IAddWebServiceForUniqueConstraint.class)
    private String email;

    
    @Pattern(regexp = Constants.NOT_BLANK_REGEX, message = "Adress can't be empty",groups = IWebServicesValidators.class)
    @Size(min = 3, max = 50, message = "Adress value '${validatedValue}' must be between {min} and {max} characters long", groups = IWebServicesValidators.class)
    @ApiModelProperty(required = true, value = "User's adress")
    private String address;

    @ApiModelProperty(required = true, value = "User's phone number")
    @JsonIgnore
    private String phone;

    @ApiModelProperty(required = false, value = "User's birth date", dataType = "Date")
    @Temporal(TemporalType.DATE)
    @Past(message = "Date of birth must be in the past", groups = IWebServicesValidators.class)
    private Date dateOfBirth;

   
    @Pattern(regexp = Constants.NOT_BLANK_REGEX, message = "Username can't be empty",groups = IWebServicesValidators.class)
    @Size(min = 5, max = 20, message = "Username value '${validatedValue}' must be between {min} and {max} characters long", groups = IWebServicesValidators.class)
    @ApiModelProperty(required = true, value = "Username")
    @Unique(className = User.class, attributName = "username", message = "Username ${validatedValue} already exist", groups = IAddWebServiceForUniqueConstraint.class)
    private String username;

    @Pattern(regexp = Constants.PASSWORD_REGEX, message = Constants.PASSWORD_ERROR_MESSAGE,groups = IWebServicesValidators.class)
    @ApiModelProperty(required = true, value = "User's password")
    private String password;

    @ApiModelProperty(required = false, value = "User's enabled")
    private boolean enabled;

   
    @ApiModelProperty(required = false, value = "User's roles")
    @JsonManagedReference
    @XmlTransient
    @JsonIgnore
    private Set<RoleDto> roles = new HashSet<>(0);

    public UserDto() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public Date getDateOfBirth() {
        return new Date(dateOfBirth.getTime());
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = new Date(dateOfBirth.getTime());
    }

   

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + id;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserDto)) {
            return false;
        }
        UserDto other = (UserDto) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserDto [id=").append(id).append(", firstname=").append(firstname).append(", lastname=").append(lastname).append(", email=").append(email)
                .append(", address=").append(address).append(", phone=").append(phone).append(", dateOfBirth=").append(dateOfBirth).append(", username=").append(username)
                .append(", password=").append(password).append(", enabled=").append(enabled).append(", roles=").append(roles).append(']');
        return builder.toString();
    }

}
