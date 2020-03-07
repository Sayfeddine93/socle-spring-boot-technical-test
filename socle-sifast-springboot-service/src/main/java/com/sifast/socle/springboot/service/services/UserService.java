package com.sifast.socle.springboot.service.services;

import com.sifast.socle.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * save User
     * @param user
     * @return User
     */
    User save(User user);

    /**
     * find User By userName
     * @param username
     * @return User
     */
    User findByUsername(String username);

    /**
     * find All Users
     * @return List of Users
     */
    List<User> findAll();

    /**
     * find User By Id
     * @param id
     * @return User
     */
    Optional<User> findById(int id);

    /**
     * delete User By Id
     * @param id
     */
    void deleteUserById(int id);
}
