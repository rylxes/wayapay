package com.wayapay.payment.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wayapay.payment.misc.AccountStatus;
import com.wayapay.payment.misc.AccountTier;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


@FilterDef(name = "myAccount", parameters = @ParamDef(name = "user_name", type = "String"))
@Filter(name = "myAccount", condition = "user_name = :user_name")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class UserAccount extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "username",
            nullable = false,
            referencedColumnName = "username"
    )
    @JsonBackReference
    private User user;
    private String customerAccountId;
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    private AccountTier accountTier = AccountTier.LEVEL_1;
    private BigDecimal balance = new BigDecimal(0);
    private BigDecimal maxSpending;

}

