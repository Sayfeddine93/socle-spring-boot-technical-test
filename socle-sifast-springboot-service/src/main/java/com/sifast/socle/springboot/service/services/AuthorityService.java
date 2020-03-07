package com.sifast.socle.springboot.service.services;

import com.sifast.socle.springboot.model.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {

    /**
     * find All Authorities
     * @return List of Authorities
     */
    List<Authority> findAll();

    /**
     * find Authority By designation
     * @param designation
     * @return Authority
     */
    Authority findAuthorityByDesignation(String designation);

    /**
     * save Authority
     * @param authority
     * @return Authority
     */
    Authority save(Authority authority);

    /**
     * find Authority By Id
     * @param id
     * @return Optional of Authority
     */
    Optional<Authority> findById(int id);

    /**
     * delete Authority By Id
     * @param id
     */
    void deleteById(int id);
}
