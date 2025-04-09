package com.domain.repo;

import com.domain.model.CustomerLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLanguageRepository extends JpaRepository<CustomerLanguage, Integer> {
}
