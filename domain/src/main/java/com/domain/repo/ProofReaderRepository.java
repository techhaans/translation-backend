package com.domain.repo;
import com.domain.model.ProofReaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProofReaderRepository extends JpaRepository<ProofReaders, Integer> {
    Optional<ProofReaders> findByRole(String role);
}

