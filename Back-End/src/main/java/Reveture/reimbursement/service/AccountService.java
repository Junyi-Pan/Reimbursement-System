package Reveture.reimbursement.service;

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

    public AccountResponse register(Account account) throws DuplicateUsernameException{
        Account check = accountRepository.findAccountByUsername(account.getUsername());
        if (check != null) throw new DuplicateUsernameException();
        Account res = accountRepository.save(account);
        AccountResponse result = new AccountResponse(res.getAccountId(), res.getManager());
        return result;
    }

    public AccountResponse login(Account account) {
        Account check = accountRepository.findAccountByUsername(account.getUsername());
        if (check == null) return null;
        if (!check.getPassword().equals(account.getPassword())) return null;
        AccountResponse result = new AccountResponse(check.getAccountId(), check.getManager());
        return result;
    }
}
