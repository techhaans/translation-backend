package com.domain.serviceImpl;

import com.domain.model.Language;
import com.domain.repo.LanguageRepository;
import com.domain.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public List<Language> getLanguageByid(Integer id) {
        return languageRepository.findByid(id);
    }
}
