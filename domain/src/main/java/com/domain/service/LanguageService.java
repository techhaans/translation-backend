package com.domain.service;

import com.domain.model.Language;

import java.util.List;

public interface LanguageService {
    List<Language> getAllLanguages();

    List<Language> getLanguageByid(Integer id);
}
