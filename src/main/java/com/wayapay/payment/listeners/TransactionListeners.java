package com.wayapay.payment.listeners;

import com.wayapay.payment.dto.DataDTO;
import com.wayapay.payment.dto.NotificationDTO;
import com.wayapay.payment.dto.RecipientDTO;
import com.wayapay.payment.dto.TransactionDTO;
import com.wayapay.payment.events.*;
import com.wayapay.payment.misc.AccountStatus;
import com.wayapay.payment.misc.TransactionType;
import com.wayapay.payment.models.entities.TierLimit;
import com.wayapay.payment.models.entities.UserAccount;
import com.wayapay.payment.models.repositories.TierLimitRepository;
import com.wayapay.payment.models.repositories.TransactionRepository;
import com.wayapay.payment.models.repositories.UserAccountRepository;
import com.wayapay.payment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Slf4j
public class TransactionListeners {

    private final UserService userService;
    private final UserAccountRepository userAccountRepository;
    private final TierLimitRepository tierLimitRepository;
    private final TransactionRepository transactionRepository;

    public TransactionListeners(TierLimitRepository tierLimitRepository,
                                TransactionRepository transactionRepository,
                                UserService userService,
                                UserAccountRepository userAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.tierLimitRepository = tierLimitRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @EventListener
    public void sufficientFundsCheck(SufficientFundsCheck sufficientFundsCheck) {
        log.info("sufficientFundsCheck " + sufficientFundsCheck.toString());

        TransactionDTO transactionDTO = sufficientFundsCheck.getTransactionDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        UserAccount accounts = userAccountRepository.findAccountWithUserAndAccount(user.getUsername(), transactionDTO.getDebitAccountNo()).orElseThrow(() -> new RuntimeException("Account Not Found for User"));
        if (transactionDTO.getAmount().longValue() > accounts.getBalance().longValue()) {
            throw new RuntimeException("Insufficient Fund for account : " + transactionDTO.getDebitAccountNo());
        }

    }


    @EventListener
    public void accountClosedCheck(AccountClosedCheck accountClosedCheck) {
        log.info("accountClosedCheck " + accountClosedCheck.toString());

        String accountNumber = accountClosedCheck.getAccountNumber();
        UserAccount accounts = userAccountRepository.findAccountWithUser(accountNumber).orElseThrow(() -> new RuntimeException("Account Not Found"));
        if (accounts.getAccountStatus().equals(AccountStatus.CLOSED)) {
            throw new RuntimeException("account : " + accountNumber + " Closed");
        }

    }


    @EventListener
    public void transactionNotification(TransactionNotification transactionNotification) {
        log.info("transactionNotification " + transactionNotification.toString());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        com.wayapay.payment.models.entities.User accountUser = userService.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + user.getUsername()));


        NotificationDTO notificationDTO = new NotificationDTO();
        DataDTO dataDTO = new DataDTO();
        RecipientDTO recipientDTO = new RecipientDTO();
        recipientDTO.setEmail(accountUser.getEmail());
        recipientDTO.setTelephone(accountUser.getMobile());
        dataDTO.setMessage("Transaction Successful");
        dataDTO.setRecipients(recipientDTO);
        notificationDTO.setData(dataDTO);
        notificationDTO.setEventType(transactionNotification.getTransactionDTO().getTranType());
        notificationDTO.setInitiator(user.getUsername());

        // This is where notification endpoint would be called with the parameters
    }


    @EventListener
    public void updateCustomerBalance(UpdateCustomerBalance updateCustomerBalance) {
        log.info("updateCustomerBalance " + updateCustomerBalance.toString());

        String accountNumber = updateCustomerBalance.getAccountNumber();
        BigDecimal amount = updateCustomerBalance.getAmount();
        TransactionType transactionType = updateCustomerBalance.getTransactionType();

        BigDecimal total = new BigDecimal(0);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        UserAccount accounts = userAccountRepository.findAccountWithUserAndAccount(user.getUsername(), accountNumber).orElseThrow(() -> new RuntimeException("Account Not Found for User"));

        try {
            if (transactionType.equals(TransactionType.CREDIT)) {
                total = accounts.getBalance().add(amount);
                accounts.setBalance(total);
            }
            if (transactionType.equals(TransactionType.DEBIT)) {
                total = accounts.getBalance().subtract(amount);
                accounts.setBalance(total);
            }
            userAccountRepository.save(accounts);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("An Error has occurred while processing your request");
        }


    }

    @EventListener
    public void debitRestrictionCheck(DebitRestrictionCheck debitRestrictionCheck) {
        log.info("debitRestrictionCheck " + debitRestrictionCheck.toString());

        TransactionDTO transactionDTO = debitRestrictionCheck.getTransactionDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        UserAccount accounts = userAccountRepository.findAccountWithUserAndAccount(user.getUsername(), transactionDTO.getDebitAccountNo()).orElseThrow(() -> new RuntimeException("Account Not Found"));


        BigDecimal transactionAmout = transactionRepository.transactionAmountSource(transactionDTO.getDebitAccountNo(), TransactionType.DEBIT);
        BigDecimal total = transactionAmout.add(transactionDTO.getAmount());


        if (accounts.getMaxSpending() != null) {
            if (total.longValue() > accounts.getMaxSpending().longValue()) {
                throw new RuntimeException("You have exceeded your spending limit for this account");
            }
        }

    }


    @EventListener
    public void accountTierCheck(AccountTierCheck accountTierCheck) {
        log.info("accountTierCheck " + accountTierCheck.toString());

        String accountNumber = accountTierCheck.getAccountNumber();
        BigDecimal amount = accountTierCheck.getAmount();
        TransactionType transactionType = accountTierCheck.getTransactionType();
        UserAccount accounts = userAccountRepository.findAccountWithUser(accountNumber).orElseThrow(() -> new RuntimeException("Account Not Found"));
        TierLimit tierLimit = tierLimitRepository.findTier(accounts.getAccountTier(), transactionType).orElseThrow(() -> new RuntimeException("Tier Not Set"));

        BigDecimal transactionAmout = transactionRepository.transactionAmountSource(accountNumber, transactionType);
        BigDecimal total = transactionAmout.add(amount);


        if (total.longValue() > tierLimit.getLimit().longValue()) {
            throw new RuntimeException(transactionType.toString() + " amount is above the limit for the account tier ");
        }

    }


    @EventListener
    public void accountFreezeCheck(AccountFreezeCheck accountFreezeCheck) {
        log.info("accountFreezeCheck " + accountFreezeCheck.toString());

        String accountNumber = accountFreezeCheck.getAccountNumber();
        TransactionType transactionType = accountFreezeCheck.getTransactionType();

        UserAccount accounts = userAccountRepository.findAccountWithUser(accountNumber).orElseThrow(() -> new RuntimeException("Account Not Found"));
        if (transactionType.equals(TransactionType.CREDIT) && accounts.getAccountStatus().equals(AccountStatus.CREDIT_FREEZE)) {
            throw new RuntimeException("Credit Freeze on account : " + accountNumber);
        }

        if (transactionType.equals(TransactionType.DEBIT) && accounts.getAccountStatus().equals(AccountStatus.DEBIT_FREEZE)) {
            throw new RuntimeException("Debit Freeze on account : " + accountNumber);
        }


    }
}
