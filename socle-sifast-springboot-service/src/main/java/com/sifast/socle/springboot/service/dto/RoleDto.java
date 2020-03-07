package com.sifast.socle.springboot.service.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sifast.socle.springboot.model.Role;
import com.sifast.socle.springboot.service.util.http.IAddWebServiceForUniqueConstraint;
import com.sifast.socle.springboot.service.util.http.IWebServicesValidators;
import com.sifast.socle.springboot.service.util.http.Constants;
import com.sifast.socle.springboot.service.annotations.UniqueRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * RoleDto class
 */
@ApiModel(description = "Role")
public class RoleDto {

    @ApiModelProperty(value = "Role's identifier", readOnly = true, required = false)
    private int id;

    @Pattern(regexp = com.sifast.socle.springboot.service.util.http.Constants.NOT_BLANK_REGEX, message = "Role designation can't be empty",groups = IWebServicesValidators.class)
    @Size(min = 5, message = "Designation value '${validatedValue}' must have a minimum {min} characters long", groups = { IWebServicesValidators.class })
    @ApiModelProperty(value = "Role's designation", required = true)
    @Pattern(regexp = "^ROLE_[A-Z]*", message = "designation must start with ROLE_ and contains only upper case characters", groups = { IWebServicesValidators.class })
    @UniqueRole(className = Role.class, attributName = "designation", message = "Designation ${validatedValue} already exist", groups = IAddWebServiceForUniqueConstraint.class)
    private String designation;

    @Pattern(regexp = Constants.NOT_BLANK_REGEX, message = "Role descritpion can't be empty",groups = IWebServicesValidators.class)
    @Size(min = 10, message = "Descritpion value '${validatedValue}' must have a minimum {min} characters long", groups = { IWebServicesValidators.class })
    @ApiModelProperty(value = "Role's description", required = true)
    private String description;

    private Set<AuthorityDto> authorities = new HashSet<>(0);

    public RoleDto() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AuthorityDto> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityDto> authorities) {
        this.authorities = authorities;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        RoleDto other = (RoleDto) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RoleDto [id=").append(id).append(", designation=").append(designation).append(", description=").append(description).append(", authorities=")
                .append(authorities).append(']');
        return builder.toString();
    }

}
