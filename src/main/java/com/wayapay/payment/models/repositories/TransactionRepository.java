package com.wayapay.payment.models.repositories;

import com.wayapay.payment.misc.TransactionType;
import com.wayapay.payment.models.entities.Transactions;
import com.wayapay.payment.models.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionRepository extends JpaSpecificationExecutor<Transactions>, CrudRepository<Transactions, Long> {

//    @Transactional
//    @Query(value = "SELECT SUM(amount) from #{#entityName} where username =:userName and transactionType =:transactionType")
//    BigDecimal findTransaction(@Param("userName") String userName, @Param("transactionType") TransactionType transactionType);


    @Transactional
    @Query(value = "SELECT SUM(amount) from #{#entityName} where sourceAccountNumber =:accountNumber and transactionType =:transactionType")
    BigDecimal transactionAmountSource(@Param("accountNumber") String accountNumber, @Param("transactionType") TransactionType transactionType);


    @Transactional
    @Query(value = "SELECT SUM(amount) from #{#entityName} where destinationAccountNumber =:accountNumber and transactionType =:transactionType")
    BigDecimal transactionAmountDest(@Param("accountNumber") String accountNumber, @Param("transactionType") TransactionType transactionType);



}
