package com.domain.repo;
import com.domain.model.LabelTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelTranslationRepository extends JpaRepository<LabelTranslation, Integer> {
}