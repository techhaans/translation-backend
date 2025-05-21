package com.domain.repo;
import com.domain.model.Customer;
import java.util.Optional;
import java.util.UUID;

import com.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByCuid(UUID cuid);

    Customer findByUser_Id(int id);

    Optional<Customer> findByUserId(Long id);
}