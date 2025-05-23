package com.dc.facade.fd;
import com.domain.model.Language;

import java.util.List;

public interface LanguageFacade {

    List<Language> getAllLanguages();

    List<Language> getLanguagesByCustomerId(Integer customerId);
}
