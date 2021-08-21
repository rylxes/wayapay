package com.wayapay.payment.models.repositories;

import com.wayapay.payment.misc.AccountTier;
import com.wayapay.payment.misc.TransactionType;
import com.wayapay.payment.models.entities.TierLimit;
import com.wayapay.payment.models.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TierLimitRepository extends JpaSpecificationExecutor<TierLimit>, CrudRepository<TierLimit, Long> {

    @Transactional
    @Query(value = "from #{#entityName} where accountTier =:accountTier and transactionType =:transactionType")
    Optional<TierLimit> findTier(@Param("accountTier") AccountTier accountTier, @Param("transactionType") TransactionType transactionType);

}
