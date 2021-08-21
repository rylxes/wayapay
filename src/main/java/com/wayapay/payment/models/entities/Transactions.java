package com.wayapay.payment.models.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wayapay.payment.misc.TransactionType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Transactions extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    private BigDecimal amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "username",
            nullable = false,
            referencedColumnName = "username"
    )
    @JsonBackReference
    private User user;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String destinationAccountName;
    private String destinationBankCode;
    private String destinationBankName;
    private String currencyCode;
    private Double chargedFee;
    private String status;
    private TransactionType transactionType;

    private String paymentReference;
    private String tranNarration;



}
