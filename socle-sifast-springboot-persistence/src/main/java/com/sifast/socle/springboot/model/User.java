package com.sifast.socle.springboot.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * User Entity Class
 */
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    private int id;

    @Column(name = "USR_FIRSTNAME")
    private String firstname;

    @Column(name = "USR_LASTNAME")

    private String lastname;

    @Column(name = "USR_EMAIL", nullable = true, length = 200, unique = true)

    private String email;

    @Column(name = "USR_ADRESS", nullable = true, length = 200)

    private String adress;

    @Column(name = "USR_PHONE", nullable = true, length = 30)

    private String phone;

    @Column(name = "USR_BIRTHDATE", length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @Column(name = "USR_LOGIN", nullable = false, unique = true, length = 200)

    private String username;

    @Column(name = "USR_PASSWORD", nullable = false, length = 200)

    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "TJ_USER_ROLE", joinColumns = {@JoinColumn(name = "USR_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROL_ID")})
    private Set<Role> roles = new HashSet<>();

    // default constructor
    public User() {
    }

    public int getId() {
        return id;
    }

    // getters and setters
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=");
        builder.append(id);
        builder.append(", firstname=");
        builder.append(firstname);
        builder.append(", lastname=");
        builder.append(lastname);
        builder.append(", email=");
        builder.append(email);
        builder.append(", adress=");
        builder.append(adress);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", dateOfBirth=");
        builder.append(dateOfBirth);
        builder.append(", username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", roles=");
        builder.append(roles);
        builder.append("]");
        return builder.toString();
    }

}
