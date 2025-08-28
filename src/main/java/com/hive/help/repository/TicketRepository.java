package com.hive.help.repository;

import com.hive.help.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByCreatorId(Long creatorId, Pageable pageable);

    Page<Ticket> findByAssignedTechnicianId(Long technicianId, Pageable pageable);

    Page<Ticket> findByStatusAndAssignedTechnicianId(TicketStatus status, Long techId, Pageable pageable);

    @Query("""
      SELECT t FROM Ticket t
      WHERE FUNCTION('TIMESTAMPDIFF', HOUR, t.createdAt, CURRENT_TIMESTAMP) < 24
    """)
    Page<Ticket> findGreen(Pageable pageable);

    @Query("""
      SELECT t FROM Ticket t
      WHERE FUNCTION('TIMESTAMPDIFF', HOUR, t.createdAt, CURRENT_TIMESTAMP) BETWEEN 24 AND 48
    """)
    Page<Ticket> findOrange(Pageable pageable);

    @Query("""
      SELECT t FROM Ticket t
      WHERE FUNCTION('TIMESTAMPDIFF', HOUR, t.createdAt, CURRENT_TIMESTAMP) > 48
    """)
    Page<Ticket> findRed(Pageable pageable);
}
