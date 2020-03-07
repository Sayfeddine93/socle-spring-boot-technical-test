package com.sifast.socle.springboot.service.services;


import com.sifast.socle.springboot.model.Role;

import javax.transaction.Transactional;
import java.util.List;

/**
 * RoleService Interface
 */
public interface RoleService {

    /**
     * find Role By designation
     * @param designation
     * @return Role
     */
    Role findRoleByDesignation(String designation);

    /**
     * save Role
     * @param role
     * @return Role
     */
    Role save(Role role);

    /**
     * find all Roles
     * @return List of Roles
     */
    List<Role> findAll();

    /**
     * find Role By Id
     * @param id
     * @return Role
     */
    Role findById(int id);

    void deleteById(int id);
}