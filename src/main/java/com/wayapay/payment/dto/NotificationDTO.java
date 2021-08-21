package com.wayapay.payment.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@Setter
@ToString
public class NotificationDTO {
    private DataDTO data;
    private String eventType;
    private String initiator;
}
