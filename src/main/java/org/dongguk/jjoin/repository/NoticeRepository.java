package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
