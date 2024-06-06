package com.dws.challenge.service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.exception.InsufficientBalanceManagerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import util.ValidationUtil;

import java.math.BigDecimal;

@Slf4j
@Service
public class ValidationService {

    public void checkWithdrawable(Account bankAccount, BigDecimal amount) {
        if (ValidationUtil.isNegative(bankAccount.getBalance().subtract(amount))) {
            throw InsufficientBalanceManagerException.to(
                    "Account current balance is not available to withdraw. current balance: %s, amount: %s",
                    bankAccount.getBalance(),
                    amount);
        }
    }
}
