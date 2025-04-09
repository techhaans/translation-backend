package com.domain.repo;
import com.domain.model.ProofReaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProofReaderRepository extends JpaRepository<ProofReaders, Integer> {
}
