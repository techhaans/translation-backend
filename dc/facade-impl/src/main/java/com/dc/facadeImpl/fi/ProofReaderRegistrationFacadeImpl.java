package com.dc.facadeImpl.fi;

import com.dc.facade.fd.ProofReaderRegistrationFacade;
import com.domain.dto.ProofreaderRegistrationRequest;
import com.domain.model.ProofReaders;
import com.domain.model.UserTable;
import com.domain.service.ProofReaderRegistrationService;
import com.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
