package com.packetprep.system.repository;

import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    List<Day> findAllByBatch(Batch batch);

    List<Day> findByUser(User user);
}
