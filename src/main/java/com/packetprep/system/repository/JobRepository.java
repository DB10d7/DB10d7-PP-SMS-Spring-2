package com.packetprep.system.repository;



import com.packetprep.system.Model.Jobs;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Jobs, Long> {
//    List<Jobs> findByName(String name);

    Optional<Jobs> findById(Long id);

    List<Jobs> findByUser (User user);

    @Override
    void deleteById(Long id);
}
