package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByOriginName(String originName);
}
