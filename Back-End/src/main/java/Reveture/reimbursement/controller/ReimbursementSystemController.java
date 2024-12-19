package Reveture.reimbursement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import Reveture.reimbursement.entity.Account;
import Reveture.reimbursement.entity.Ticket;
import Reveture.reimbursement.exception.DuplicateUsernameException;
import Reveture.reimbursement.response.AccountResponse;
import Reveture.reimbursement.service.AccountService;
import Reveture.reimbursement.service.TicketService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/reimbursment")
public class ReimbursementSystemController {
    private final AccountService accountService;
    private final TicketService ticketService;

   
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account account) {
        try {
            AccountResponse res = accountService.register(account);
            return ResponseEntity.status(200).body(res);
        }catch (DuplicateUsernameException d) {
            return ResponseEntity.status(409).body("Username taken");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account) {
        AccountResponse res = accountService.login(account);
        if (res != null) return ResponseEntity.status(200).body(res);
        else return ResponseEntity.status(401).body("Unauthorized");
    }
    
    @PostMapping("/ticket")
    public ResponseEntity postTicket(@RequestBody Ticket ticket) {
        Ticket res = ticketService.postTicket(ticket);
        return ResponseEntity.status(200).body(res);
    }

    @GetMapping("/tickets")
    public ResponseEntity getPendingTickets() {
        return ResponseEntity.status(200).body(ticketService.getPendingTickets());
    }

    @GetMapping("/tickets/{accountId}")
    public ResponseEntity getUserTickets(@PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(ticketService.getUserTickets(accountId));
    }

    @PatchMapping("/ticket/{ticketId}/{status}")
    public ResponseEntity updateTicketStatus(@PathVariable Integer ticketId, @PathVariable String status) {
        return ResponseEntity.status(200).body(ticketService.updateTicketStatus(ticketId, status));
    }
}
