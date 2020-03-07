package com.sifast.socle.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sifast.socle.springboot.model.Role;

/**
 * RoleDao Interface
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

    /**
     * find Role By Id
     * @param id
     * @return Role
     */
    Role findById(int id);

    /**
     * find Role By designation
     * @param designation
     * @return Role
     */
    Role findByDesignation(String designation);


}
