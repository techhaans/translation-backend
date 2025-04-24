package com.dc.facade.fd;

import com.domain.dto.ProofreaderRegistrationRequest;
import java.util.List;

public interface ProofReaderRegistrationFacade {
    void registerProofreader(ProofreaderRegistrationRequest request);

    List<?> getAllProofreaders();
}
