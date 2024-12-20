package Reveture.reimbursement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Reveture.reimbursement.entity.Ticket;
import Reveture.reimbursement.repository.TicketRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class TicketService {
    TicketRepository ticketRepository;

    public Ticket postTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getPendingTickets() {
        return ticketRepository.findTicketsByPending("Pending");
    }

    public List<Ticket> getUserTickets(Integer accountId) {
        return ticketRepository.findTicketsByAccountId(accountId);
    }

    public String updateTicketStatus(Integer ticketId, String status) {
        Optional<Ticket> check = ticketRepository.findById((long)ticketId);
        if(check.isPresent()) {
            if(check.map(Ticket::getStatus).equals("Pending")) {
                ticketRepository.updateTicketStatus(ticketId, status);
                return status;
            }
        }
        return null;
        
    }
}
