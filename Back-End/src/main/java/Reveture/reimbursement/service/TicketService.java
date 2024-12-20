package Reveture.reimbursement.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Reveture.reimbursement.entity.Ticket;
import Reveture.reimbursement.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
@Transactional
public class TicketService {
    TicketRepository ticketRepository;
    private static final Logger logger = LogManager.getLogger(TicketService.class);
    public Ticket postTicket(Ticket ticket) {
        logger.info(ticket.toString());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getPendingTickets() {
        logger.info(ticketRepository.findTicketsByPending("Pending").toString());
        return ticketRepository.findTicketsByPending("Pending");
    }

    public List<Ticket> getUserTickets(Integer accountId) {
        logger.info(ticketRepository.findTicketsByAccountId(accountId).toString());
        return ticketRepository.findTicketsByAccountId(accountId);
    }

    public String updateTicketStatus(Integer ticketId, String status) {
        Optional<Ticket> check = ticketRepository.findById((long)ticketId);
        logger.info(String.valueOf(check.isPresent()));
        if(check.isPresent()) {
            logger.info(check.toString());
            if(check.map(Ticket::getStatus).equals("Pending")) {
                logger.info(String.valueOf(ticketRepository.updateTicketStatus(ticketId, status)));
                ticketRepository.updateTicketStatus(ticketId, status);
                return status;
            }
        }
        return null;
        
    }
}
