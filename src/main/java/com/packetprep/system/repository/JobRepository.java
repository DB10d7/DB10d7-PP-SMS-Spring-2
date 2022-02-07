package com.packetprep.system.repository;


import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.Jobs;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Jobs, Long> {
    List<Jobs> findByName(String userId);

    Optional<Jobs> findById(Long id);

    List<Jobs> findByUser (User user);
}
