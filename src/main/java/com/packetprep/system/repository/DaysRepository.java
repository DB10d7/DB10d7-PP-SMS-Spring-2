package com.packetprep.system.repository;

import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Days;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaysRepository extends JpaRepository<Days, Long> {
    List<Days> findAllByBatch(Batch batch);

    List<Days> findByUser(User user);
}
