package com.sifast.socle.springboot.dao;

import com.sifast.socle.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDao Interface
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    /**
     * find User By userName
     * @param username
     * @return User
     */
    User findByUsername(String username);

    /**
     * find Users By lastName
     * @param lastName
     * @return List Of Users
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.lastname) = LOWER(:lastName)")
    public List<User> find(@Param("lastName") String lastName);
}
