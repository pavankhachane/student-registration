package com.pratiksha.studentregistration.repository;

import com.pratiksha.studentregistration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.username = :username, u.password = :password, u.email = :email where u.id = :id")
    void updateUser(@Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("id") Long id);



}

