package com.wayapay.payment.misc;

import lombok.Getter;
import lombok.Setter;


public enum TransactionType {
    DEBIT("debit"),
    CREDIT("credit");


    private final String transactionType;

    private TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getValue() {
        return this.transactionType;
    }

    public String valueOf() {
        return this.transactionType;
    }

    public static TransactionType textValueOf(String textValue) {
        TransactionType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            TransactionType value = var1[var3];
            if (textValue.equalsIgnoreCase(value.transactionType)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid EventType {" + textValue + "}");
    }
}
