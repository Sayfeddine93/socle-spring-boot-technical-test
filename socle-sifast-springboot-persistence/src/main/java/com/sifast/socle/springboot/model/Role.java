package com.sifast.socle.springboot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "T_ROLE")

/**
 * Role Entity Class
 */
public class Role implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROL_ID")

    private int id;

    @Column(name = "ROL_DESIGNATION", unique = true, nullable = false)

    private String designation;

    @Column(name = "ROL_DESCRIPTION", unique = false, nullable = false)

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "TJ_AUTH_ROLE", joinColumns = {@JoinColumn(name = "ROL_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTH_ID")})
    private Set<Authority> authorities = new HashSet<>();

    // default constructor
    public Role() {
    }

    // constructor using field designation
    public Role(String designation) {
        super();
        this.designation = designation;
    }

    // getters and setters
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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    // hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Role [id=");
        builder.append(id);
        builder.append(", designation=");
        builder.append(designation);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public String getAuthority() {
        return designation;
    }

}
