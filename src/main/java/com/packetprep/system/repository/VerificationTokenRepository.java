package com.packetprep.system.repository;

import com.packetprep.system.Model.User;
import com.packetprep.system.Model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
   Optional<VerificationToken> findByToken(String token);

   List<VerificationToken> findByUser(User user);

   @Override
   void delete(VerificationToken entity);
}
