package com.wayapay.payment.events;


import com.wayapay.payment.misc.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@AllArgsConstructor
@Setter
@Getter
public class UpdateCustomerBalance {
    String accountNumber;
    BigDecimal amount;
    TransactionType transactionType;
}
