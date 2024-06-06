package com.dws.challenge.repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.AccountNotFoundException;
import com.dws.challenge.exception.DuplicateAccountIdException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public void createAccount(Account account) throws DuplicateAccountIdException {
        Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
        if (previousAccount != null) {
            throw new DuplicateAccountIdException(
                    "Account id " + account.getAccountId() + " already exists!");
        }
        System.out.println("Creating account " + account.getAccountId());
    }

    @Override
    public Account getAccount(String accountId) {
        Account account = accounts.get(accountId);
       if (account == null) {
            throw new AccountNotFoundException(
                    "Account id " + accountId + " doesnot exists!");
       }

        return account;
    }

    @Override
    public void clearAccounts() {
        accounts.clear();
    }

    @Override
    public void decreaseCurrentBalance(String accountId, BigDecimal amount){
        Account account = accounts.get(accountId);
        account.setBalance(account.getBalance().subtract(amount));
        accounts.put(accountId, account);
    }

    @Override
    public void increaseCurrentBalance(String accountId, BigDecimal amount){
        Account account = accounts.get(accountId);
        account.setBalance(account.getBalance().add(amount));
        accounts.put(accountId, account);
    }

}
