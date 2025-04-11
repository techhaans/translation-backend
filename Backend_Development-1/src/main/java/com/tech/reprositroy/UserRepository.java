package com.tech.reprositroy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.model.User;
@Repository
public interface UserRepository  extends JpaRepository<User, Long>
{
    Optional<User> findByNameAndPasswordAndRole(String name, String password, String role);

}
