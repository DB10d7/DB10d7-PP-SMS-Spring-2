package com.packetprep.system.repository;

import com.packetprep.system.Model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    Optional<Batch> findByName(String batchName);
}
