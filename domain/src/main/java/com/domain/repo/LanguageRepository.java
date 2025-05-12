package com.domain.repo;

import com.domain.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    Language findByLanguageKey(String languageKey);

    List<Language> findByid(Integer id);
}