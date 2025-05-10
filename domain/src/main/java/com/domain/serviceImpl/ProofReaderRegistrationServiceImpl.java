package com.domain.serviceImpl;

import com.domain.dto.ProofreaderRegistrationRequest;
import com.domain.model.ProofReaders;
import com.domain.model.UserTable;
import com.domain.repo.ProofReaderRepository;
import com.domain.repo.UserRepository;
import com.domain.service.ProofReaderRegistrationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProofReaderRegistrationServiceImpl implements ProofReaderRegistrationService {

    @Autowired
    private ProofReaderRepository proofreaderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void saveProofReader(ProofreaderRegistrationRequest request) {
        UserTable user = new UserTable();
        user.setName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("PROOFREADER");
        userRepository.save(user);

        ProofReaders proofReader = new ProofReaders();
        proofReader.setPuid(UUID.randomUUID());
        proofReader.setProofReaderName(request.getName());
        proofReader.setCountry(request.getCountry());
        proofReader.setSupportedLanguages(String.join(",", request.getSupportedLanguages()));
        proofReader.setUser(user);
        proofReader.setStatus("ACTIVE");
        proofReader.setRole("PROOFREADER");
        proofReader.setCreatedDate(LocalDateTime.now());
        proofReader.setUpdatedDate(LocalDateTime.now());

        proofreaderRepository.save(proofReader);
    }

    @Override
    public List<ProofReaders> getAllProofReaders() {
        return proofreaderRepository.findAll();
    }
}
