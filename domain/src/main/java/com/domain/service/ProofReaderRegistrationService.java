package com.domain.service;

import com.domain.dto.ProofreaderRegistrationRequest;
import com.domain.model.ProofReaders;

import java.util.List;

public interface ProofReaderRegistrationService {
    void saveProofReader(ProofreaderRegistrationRequest proofReader);

    List<ProofReaders> getAllProofReaders();
}
