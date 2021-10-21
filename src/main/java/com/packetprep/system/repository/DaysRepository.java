package com.packetprep.system.repository;

import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Days;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DaysRepository extends JpaRepository<Days, Long> {
    Optional<Days> findByBatch(String dayName);
}
