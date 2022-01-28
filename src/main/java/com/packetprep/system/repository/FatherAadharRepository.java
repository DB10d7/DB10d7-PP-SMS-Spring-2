package com.packetprep.system.repository;

import com.packetprep.system.Model.FatherAadhar;
import com.packetprep.system.Model.Image;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FatherAadharRepository extends JpaRepository<FatherAadhar, Long> {
    Optional<FatherAadhar> findById(Long id);

    Optional<FatherAadhar> findByUser(User user);

    @Override
    void delete(FatherAadhar entity);
}
