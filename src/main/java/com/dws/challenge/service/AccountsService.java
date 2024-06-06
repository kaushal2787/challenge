package com.dws.challenge.service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.repository.AccountsRepository;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.locks.Lock;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  private final ValidationService validationService;

  private final BankAccountService bankAccountService;

  private final EmailNotificationService emailNotificationService;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository, ValidationService validationService, BankAccountService bankAccountService, EmailNotificationService emailNotificationService) {
    this.accountsRepository = accountsRepository;
    this.validationService = validationService;
    this.bankAccountService = bankAccountService;
    this.emailNotificationService = emailNotificationService;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

  public boolean transfer(String fromBankAccountId, String toBankAccountId, BigDecimal amount) throws Exception {
    Account fromBankAccount = this.accountsRepository.getAccount(fromBankAccountId);
    Account toBankAccount = this.accountsRepository.getAccount(toBankAccountId);
    Random rand = new Random();

    // validate parameters
    Preconditions.checkNotNull(fromBankAccountId, "fromBbankAccount can not be null");
    Preconditions.checkNotNull(toBankAccountId, "toBankAccount can not be null");
    Preconditions.checkArgument(!Objects.equals(fromBankAccount.getAccountId(), toBankAccount.getAccountId()),
            "Transfer can not executed an account to the same account. bankAccountId: ",
            fromBankAccount.getAccountId());

    Lock fromBankAccountTransferLock = fromBankAccount.getTransferLock();
    Lock toBankAccountTransferLock = toBankAccount.getTransferLock();

    if (fromBankAccountTransferLock.tryLock()){
        fromBankAccount.getLock().writeLock();
        if (toBankAccountTransferLock.tryLock()){
          try{
            takeMoney(fromBankAccount, amount, toBankAccountId);
            putMoney(toBankAccount, amount, fromBankAccountId);
          } catch (Exception e) {
            return false;
          } finally {
            toBankAccountTransferLock.unlock();
          }
        }
      fromBankAccountTransferLock.unlock();
      Thread.sleep(rand.nextInt(1001))  ;
    }
    return true;
  }


  private void takeMoney(Account bankAccount, BigDecimal amount, String toBankAccountId) throws Exception {

    validationService.checkWithdrawable(bankAccount, amount);

    Account updatedBankAccount = bankAccountService.decreaseCurrentBalance(bankAccount, amount);
    emailNotificationService.notifyAboutTransfer(updatedBankAccount, "amount:"+amount+" "+ "has been withdrawn by user:"+" "+toBankAccountId);
  }

  private void putMoney(Account bankAccount, BigDecimal amount, String fromBankAccountId) throws Exception {

    Account updatedBankAccount = bankAccountService.increaseCurrentBalance(bankAccount, amount);
    emailNotificationService.notifyAboutTransfer(updatedBankAccount, "amount:"+amount+" "+ "has been deposited by user:"+" "+fromBankAccountId);
  }
}
