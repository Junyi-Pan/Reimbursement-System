package Reveture.reimbursement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Reveture.reimbursement.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

    @Query("SELECT t FROM Ticket t INNER JOIN Account a ON t.postedBy = a.accountId WHERE a.accountId = ?1 ORDER BY t.ticketId DESC")
    List<Ticket> findTicketsByAccountId(Integer accountId);

    @Query("SELECT t FROM Ticket t WHERE status = ?1 ORDER BY ticketId DESC")
    List<Ticket> findTicketsByPending(String status);

    @Modifying
    @Query("UPDATE Ticket SET status = ?2 WHERE ticketId = ?1")
    int updateTicketStatus(Integer ticketId, String status);
}
