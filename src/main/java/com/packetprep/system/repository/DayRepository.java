package com.packetprep.system.repository;

import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

    Optional<Day> findByName(String dayName);

    Optional<Day> findById(Long id);

    List<Day> findAllByBatch(Batch batch);

    List<Day> findByUser(User user);

    @Override
    void delete(Day entity);

    //  List<Day> findAllByStudents(Student student);


}
