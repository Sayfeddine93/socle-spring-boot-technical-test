package com.sifast.socle.springboot.service.services.impl;

import com.sifast.socle.springboot.dao.RoleDao;
import com.sifast.socle.springboot.model.Role;
import com.sifast.socle.springboot.service.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoleServiceImpl class
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findRoleByDesignation(String designation) {
        return roleDao.findByDesignation(designation);
    }

    @Override
    public Role save(Role role) {
        return roleDao.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findById(int id) {
        return roleDao.findById(id);
    }

    @Override
    public void deleteById(int id) {
        roleDao.deleteById(id);
    }
}