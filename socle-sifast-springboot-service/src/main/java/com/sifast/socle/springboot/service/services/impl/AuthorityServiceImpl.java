package com.sifast.socle.springboot.service.services.impl;

import com.sifast.socle.springboot.dao.AuthorityDao;
import com.sifast.socle.springboot.model.Authority;
import com.sifast.socle.springboot.service.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public List<Authority> findAll() {
        return authorityDao.findAll();
    }

    @Override
    public Authority findAuthorityByDesignation(String designation) {
        return authorityDao.findAuthorityByDesignation(designation);
    }

    @Override
    public Authority save(Authority authority) {
        return authorityDao.save(authority);
    }

    @Override
    public Optional<Authority> findById(int id) {
        return authorityDao.findById(id);
    }

    @Override
    public void deleteById(int id) {
        authorityDao.deleteById(id);
    }
}
