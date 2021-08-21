package com.wayapay.payment.misc;

public enum AccountStatus {
    CLOSED,
    DEBIT_FREEZE,
    CREDIT_FREEZE,
    ACTIVE;

    private AccountStatus() {
    }
}
