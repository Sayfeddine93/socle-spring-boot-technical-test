package com.sifast.socle.springboot.dao;

import com.sifast.socle.springboot.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AuthorityDao interface
 */
@Repository
public interface AuthorityDao extends JpaRepository<Authority, Integer> {

    /**
     * find Authority By Designation
     * @param designation of authority
     * @return Authority class
     */
    Authority findAuthorityByDesignation(String designation);

}
