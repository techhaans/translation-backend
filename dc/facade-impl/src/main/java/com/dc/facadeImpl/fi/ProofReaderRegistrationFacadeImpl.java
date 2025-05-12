package com.dc.facadeImpl.fi;

import com.dc.facade.fd.ProofReaderRegistrationFacade;
import com.domain.dto.ProofreaderRegistrationRequest;
import com.domain.service.ProofReaderRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProofReaderRegistrationFacadeImpl implements ProofReaderRegistrationFacade {

    @Autowired
    private ProofReaderRegistrationService proofReaderRegistrationService;
    @Override
    public void registerProofreader(ProofreaderRegistrationRequest request) {
        proofReaderRegistrationService.saveProofReader(request);
    }
    @Override
    public List<?> getAllProofreaders() {
        return proofReaderRegistrationService.getAllProofReaders();
    }
}
