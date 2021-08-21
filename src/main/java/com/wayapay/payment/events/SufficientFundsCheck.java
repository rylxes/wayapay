package com.wayapay.payment.events;


import com.wayapay.payment.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Setter
@Getter
public class SufficientFundsCheck {
    TransactionDTO transactionDTO;
}
