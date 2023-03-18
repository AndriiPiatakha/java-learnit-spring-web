package com.itbulls.learnit.spring.persistence.repositories.springdata;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.itbulls.learnit.spring.persistence.entities.User;

public interface UserJpaRepository<U> extends CrudRepository<User, Long> {
    List<User> findByFirstName(String firstName);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) = LOWER(:firstName)")
    List<User> getByFirstNameCaseInsensitive(@Param("firstName") String firstName);
    
    
    @Query(value = "SELECT * FROM user ORDER BY first_name", nativeQuery = true)
    List<User> getAllUsersOrderByFirstName();
}