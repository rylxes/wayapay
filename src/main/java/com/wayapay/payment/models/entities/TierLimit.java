package com.wayapay.payment.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wayapay.payment.misc.AccountStatus;
import com.wayapay.payment.misc.AccountTier;
import com.wayapay.payment.misc.TransactionType;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class TierLimit extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    private AccountTier accountTier;
    private TransactionType transactionType;
    private BigDecimal limit = new BigDecimal(0);

}

