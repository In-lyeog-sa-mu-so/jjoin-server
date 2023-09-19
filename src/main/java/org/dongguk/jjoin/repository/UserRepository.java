package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
