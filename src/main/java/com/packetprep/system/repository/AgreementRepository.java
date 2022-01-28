package com.packetprep.system.repository;

import com.packetprep.system.Model.Agreement;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    Optional<Agreement> findById(Long id);

    Optional<Agreement> findByUser(User user);

    @Override
    void delete(Agreement entity);
}
