package com.hive.help.repository;

import com.hive.help.model.WorkLog;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    Page<WorkLog> findByTicketId(Long ticketId, Pageable pageable);
    Page<WorkLog> findByTechnicianId(Long techId, Pageable pageable);
}
