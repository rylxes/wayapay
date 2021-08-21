package com.wayapay.payment.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Getter
@Setter
@ToString
public class TransactionDTO {

    private BigDecimal amount;
    private String benefAccountNo;
    private String debitAccountNo;
    private String paymentReference;
    private String tranCrncy;
    private String tranNarration;
    private String tranType;
    private Integer userId;

}
