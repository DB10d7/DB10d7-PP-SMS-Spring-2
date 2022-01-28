package com.packetprep.system.repository;

import com.packetprep.system.Model.Resume;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume,Long> {

    Optional<Resume> findById(Long id);
    Optional<Resume> findByUser(User user);

    @Override
    void delete(Resume entity);
}
