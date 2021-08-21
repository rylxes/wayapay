package com.wayapay.payment.events;


import com.wayapay.payment.dto.TransactionDTO;
import com.wayapay.payment.misc.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@AllArgsConstructor
@Setter
@Getter
public class AccountTierCheck {
    String accountNumber;
    BigDecimal amount;
    TransactionType transactionType;
}
