package com.wayapay.payment.service;


import com.wayapay.payment.dto.TransactionDTO;
import com.wayapay.payment.dto.TransactionResponseDTO;

public interface TransactionService {

    TransactionResponseDTO create(TransactionDTO transactionDTO);

}
