package com.domain.repo;
import com.domain.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Integer> {
    Optional<UserTable> findByName(String name);

    Optional<UserTable> findByNameAndPasswordAndRole(String name, String password, String role);

    Optional<UserTable> findByNameAndRole(String name, String role);
}
