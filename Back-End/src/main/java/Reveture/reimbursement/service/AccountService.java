package Reveture.reimbursement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Reveture.reimbursement.entity.Account;
import Reveture.reimbursement.exception.DuplicateUsernameException;
import Reveture.reimbursement.repository.AccountRepository;
import Reveture.reimbursement.response.AccountResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional(rollbackFor = DuplicateUsernameException.class)
public class AccountService {
    AccountRepository accountRepository;
    private static final Logger logger = LogManager.getLogger(TicketService.class);
    public AccountResponse register(Account account) throws DuplicateUsernameException{
        Account check = accountRepository.findAccountByUsername(account.getUsername());
        if (check != null) throw new DuplicateUsernameException();
        Account res = accountRepository.save(account);
        logger.info(res.getAccountId());
        logger.info(res.getUsername());
        logger.info(res.getPassword());
        logger.info(res.getManager());
        AccountResponse result = new AccountResponse(res.getAccountId(), res.getManager());
        return result;
    }

    public AccountResponse login(Account account) {
        Account check = accountRepository.findAccountByUsername(account.getUsername());
        
        if (check == null) {
            logger.info("No account with username");
            return null;
        } 
        if (!check.getPassword().equals(account.getPassword())) {
            logger.info("Password incorrect");
            return null;
        }
        logger.info(check.getAccountId());
        logger.info(check.getManager());
        AccountResponse result = new AccountResponse(check.getAccountId(), check.getManager());
        return result;
    }
}
