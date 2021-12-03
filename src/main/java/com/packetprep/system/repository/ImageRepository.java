package com.packetprep.system.repository;

import com.packetprep.system.Model.Image;
import com.packetprep.system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image , Long> {
    Optional<Image> findById(Long id);

    Optional<Image> findByUser(User user);

    @Override
    void delete(Image entity);
}
