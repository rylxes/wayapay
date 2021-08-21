package com.wayapay.payment.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class DataDTO {

    private String message;
    private RecipientDTO recipients;
}
