package com.dws.challenge.service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.repository.AccountsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;

/**
 * BankAccount management service
 */
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class BankAccountService {

    private static final String MESSAGE_FORMAT_NO_BANK_ACCOUNT = "No bankAccount by bankAccountId: %s";

    private AccountsRepository accountsRepository;

    public Account decreaseCurrentBalance(Account bankAccount, BigDecimal amount) {
        Lock accountWriteLock = bankAccount.getLock().writeLock();
        accountWriteLock.lock();
        accountsRepository.decreaseCurrentBalance(bankAccount.getAccountId(), amount);

        Account account = accountsRepository.getAccount(bankAccount.getAccountId());
        accountWriteLock.unlock();
        return account;
    }

    public Account increaseCurrentBalance(Account bankAccount, BigDecimal amount) {
        Lock accountWriteLock = bankAccount.getLock().writeLock();
        accountWriteLock.lock();
        accountsRepository.increaseCurrentBalance(bankAccount.getAccountId(), amount);

        Account account = accountsRepository.getAccount(bankAccount.getAccountId());
        accountWriteLock.unlock();
        return account;
    }
}