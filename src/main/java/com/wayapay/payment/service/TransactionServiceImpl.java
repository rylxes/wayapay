package com.wayapay.payment.service;

import com.wayapay.payment.dto.TransactionDTO;
import com.wayapay.payment.dto.TransactionResponseDTO;
import com.wayapay.payment.events.*;
import com.wayapay.payment.misc.TransactionType;
import com.wayapay.payment.models.entities.Transactions;
import com.wayapay.payment.models.entities.User;
import com.wayapay.payment.models.repositories.TransactionRepository;
import com.wayapay.payment.models.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private ModelMapper modelMapper;


    public final UserRepository userRepository;
    public final ApplicationEventPublisher publisher;
    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public TransactionServiceImpl(ApplicationEventPublisher publisher, UserService userService, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userService = userService;
        this.publisher = publisher;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public TransactionResponseDTO create(TransactionDTO transactionDTO) {
        // Debit validation
        publisher.publishEvent(new SufficientFundsCheck(transactionDTO));
        publisher.publishEvent(new AccountClosedCheck(transactionDTO.getDebitAccountNo()));
        publisher.publishEvent(new AccountFreezeCheck(transactionDTO.getDebitAccountNo(), TransactionType.DEBIT));
        publisher.publishEvent(new DebitRestrictionCheck(transactionDTO));
        publisher.publishEvent(new AccountTierCheck(transactionDTO.getDebitAccountNo(), transactionDTO.getAmount(), TransactionType.DEBIT));


        // Credit validation
        publisher.publishEvent(new AccountClosedCheck(transactionDTO.getBenefAccountNo()));
        publisher.publishEvent(new AccountFreezeCheck(transactionDTO.getBenefAccountNo(), TransactionType.CREDIT));
        publisher.publishEvent(new AccountTierCheck(transactionDTO.getBenefAccountNo(), transactionDTO.getAmount(), TransactionType.CREDIT));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        com.wayapay.payment.models.entities.User accountUser = userService.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + user.getUsername()));
        TransactionType transactionType = TransactionType.textValueOf(transactionDTO.getTranType());

        try {
            Transactions transactions = transactionRepository.save(
                    Transactions.builder()
                            .amount(transactionDTO.getAmount())
                            .user(accountUser)
                            .sourceAccountNumber(transactionDTO.getDebitAccountNo())
                            .destinationAccountNumber(transactionDTO.getBenefAccountNo())
                            .paymentReference(transactionDTO.getPaymentReference())
                            .tranNarration(transactionDTO.getTranNarration())
                            .currencyCode(transactionDTO.getTranCrncy())
                            .transactionType(transactionType)
                            .build()
            );

            // updated Balances
            publisher.publishEvent(new UpdateCustomerBalance(transactionDTO.getBenefAccountNo(), transactionDTO.getAmount(), TransactionType.CREDIT));
            publisher.publishEvent(new UpdateCustomerBalance(transactionDTO.getDebitAccountNo(), transactionDTO.getAmount(), TransactionType.DEBIT));

            //send Notification
            publisher.publishEvent(new TransactionNotification(transactionDTO));

            TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
            transactionResponseDTO.setCode("00");
            transactionResponseDTO.setMessage("Success");

            return transactionResponseDTO;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("An Error has occurred while processing your request");
        }


    }

}
