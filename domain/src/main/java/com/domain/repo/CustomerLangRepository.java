package com.domain.repo;

import com.domain.model.CustomerLang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerLangRepository extends JpaRepository<CustomerLang, Integer> {
    List<CustomerLang> findByCustomer_Cid(Integer customerId);

    Optional<Object> findByCustomer_CidAndIsDefaultTrue(Integer customerId);
}
