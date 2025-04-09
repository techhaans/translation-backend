package com.domain.repo;
import com.domain.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Integer> {
    Optional<Languages> findByName(String name);
}