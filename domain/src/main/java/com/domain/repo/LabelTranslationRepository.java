package com.domain.repo;
import com.domain.model.LabelTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LabelTranslationRepository extends JpaRepository<LabelTranslation, Integer> {

    List<LabelTranslation> findByLabel_LabelKeyAndLanguageCode(String labelKey, String langCode);

    boolean existsByLabel_LabelKeyAndLanguageCode(String labelKey, String langCode);


    List<LabelTranslation> findByLabel_Customer_Cuid(UUID customerCuid);
}