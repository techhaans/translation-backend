package com.domain.repo;

import com.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerInfoRepository extends JpaRepository<Customer, Integer> {

  Optional<Customer> findByCuid(UUID cuid);
}
