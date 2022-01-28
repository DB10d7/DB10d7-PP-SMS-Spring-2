package com.packetprep.system.repository;

import com.packetprep.system.Model.Image;
import com.packetprep.system.Model.PassportPhoto;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportPhotoRepository extends JpaRepository<PassportPhoto, Long> {

    Optional<PassportPhoto> findById(Long id);
    Optional<PassportPhoto> findByUser(User user);

    @Override
    void delete(PassportPhoto entity);
}
