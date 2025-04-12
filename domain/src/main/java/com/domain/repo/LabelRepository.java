package com.domain.repo;

import com.domain.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
    List<Label> findByCustomer_Cid(java.lang.Integer customerId);
}

