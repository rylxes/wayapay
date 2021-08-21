package com.wayapay.payment.dto;

import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

}
