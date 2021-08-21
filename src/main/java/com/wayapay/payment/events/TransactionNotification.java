package com.wayapay.payment.events;


import com.wayapay.payment.dto.TransactionDTO;
import com.wayapay.payment.misc.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Setter
@Getter
public class TransactionNotification {
    TransactionDTO transactionDTO;
}
