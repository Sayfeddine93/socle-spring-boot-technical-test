package com.sifast.socle.springboot.service.dto;

import io.swagger.annotations.ApiModel;

/**
 * AuthorityDto class
 */
@ApiModel(description = "Authority")
public class AuthorityDto {

    private int id;

    public AuthorityDto() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AuthorityDto [id=").append(id).append(']');
        return builder.toString();
    }

}
