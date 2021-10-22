package com.packetprep.system.repository;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.Student;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByBatch(Batch batch);

    List<Student> findAllByDay(Day day);

    Optional<Student> findByName(String studentName);
}
