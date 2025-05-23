package com.domain.repo;
import com.domain.enums.Role;
import com.domain.model.ProofReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProofReaderRepository extends JpaRepository<ProofReader, Integer> {
   // Optional<ProofReader> findByRole(String role);

    Optional<ProofReader> findByUser_Role(Role adminRole);

    Optional<ProofReader> findByUserId(Long id);
}

